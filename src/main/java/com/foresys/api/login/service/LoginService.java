package com.foresys.api.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.foresys.api.login.model.LoginVO;
import com.foresys.api.system.jwt.JwtUtil;
import com.foresys.api.user.model.User;

@Service
public class LoginService {
	@Autowired
	private JwtUtil jwtUtil;
	
	public LoginVO Login(User user) throws Exception{
		
		if("error".equals(user.getId())) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
		}
		
		LoginVO vo = new LoginVO();
		vo.setAToken(jwtUtil.createAccessToken(user.getId(), ""));
		vo.setRToken(jwtUtil.createRefreshToken(user.getId(), ""));
		vo.setUserName("홍길동asd");
		
		return vo;
	}
}
