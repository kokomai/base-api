package com.foresys.api.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.foresys.api.login.model.LoginVO;
import com.foresys.api.login.service.LoginService;
import com.foresys.api.user.model.User;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/login")
@Slf4j
public class LoginController {
	@Autowired
	LoginService loginService;
	
	@PostMapping("/login")
	public LoginVO Login(@RequestBody User user) throws Exception{
		log.debug("controller entered");
		return loginService.Login(user);
	}
	
	@PostMapping("/checkLogin")
	public String GetUser(@RequestBody LoginVO token) throws Exception{
		// JwtCheckFilter will handle this..
		
		return "checked";
	}
}
