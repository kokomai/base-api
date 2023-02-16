package com.foresys.api.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
