package com.foresys.api.user.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foresys.api.user.model.User;
import com.foresys.api.user.service.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {
	
	@Autowired UserService userService;
	
	@PostMapping("/getUser")
	public User GetUser(@RequestBody User user) throws Exception{
		log.debug("user : {}", user);
		return userService.GetUser(user);
	}
	
	@GetMapping("/callGet")
	public User CallGet(User user) throws Exception{
		return user; 
	}
	
	@PostMapping("/getError")
	public User GetError(@RequestBody User user) throws Exception{
		log.debug("user : {}", user);
		return userService.GetError(user);
	}
	
	@PostMapping("/getData")
	public List<Map<String, Object>> GetRandom() throws Exception{
		Map<String, Object> map;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for(int i = 0; i < 10; i++) {
			double dValue = Math.random();
			int iValue = (int)(dValue * 10);
			map = new HashMap<String, Object>();
			map.put("key", iValue);
			
			list.add(map);
		}
		
		return list;
	}
	
	@PostMapping("/getSampleTableData")
	public Map<String, Object> GetSampleTableData(@RequestBody HashMap<String, Object> params) throws Exception{
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> map;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for(int i = 0; i < (int) params.get("rowsPerPage"); i++) {
			double dValue = Math.random();
			int iValue = (int)(dValue * 10);
			map = new HashMap<String, Object>();
			map.put("seq", i + ( ((int) params.get("page")) * (int) params.get("rowsPerPage") ));
			map.put("name", iValue + "홍길동");
			map.put("loanSeqNo", iValue * iValue * iValue * i);
			map.put("contStat", iValue % 2 == 0 ? "계약중" : "계약만료");
			
			list.add(map);
		}
		
		result.put("list", list);
		result.put("allItemCount", 100);
		
		return result;
	}
	
	@RequestMapping(path = "/sampleFileUpload", method = RequestMethod.POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public String SampleFileUpload(@RequestParam("params") String paramJson, @RequestPart("files") List<MultipartFile> files) throws Exception{
		// 파일 저장 테스트시, STS를 관리자권한으로 실행해 주시길 바랍니다.
		ObjectMapper mapper = new ObjectMapper();
		
		@SuppressWarnings("unchecked")
		HashMap<String, Object> params = mapper.readValue(paramJson, HashMap.class);
		String pathName = (String) params.get("pathName");
		File directory = new File(pathName);
	    if (! directory.exists()){
	        directory.mkdir();
	    }
		
		try {
			for(MultipartFile file : files) {
				file.transferTo(new File(pathName + file.getOriginalFilename()));
			}
		} catch(Exception e) {
			e.printStackTrace();
			return "fail";
		}
		
		return "success";
	}
	
}
