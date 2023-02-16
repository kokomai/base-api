package com.foresys.api.user.service;

import java.io.BufferedReader;
import java.io.FileReader;

import org.springframework.stereotype.Service;

import com.foresys.api.user.model.User;

@Service
public class UserService {
	
	public User GetUser(User user) throws Exception{
		user.setPw(user.getPw() + "must be hidden");
	
		return user;
	}
	
	public User GetError(User user) throws Exception{
		user.setPw(user.getPw() + "must be hidden");
		
		BufferedReader br = new BufferedReader(new FileReader("나없는파일"));
		br.readLine();
		br.close();
		
		return user;
	}

}
