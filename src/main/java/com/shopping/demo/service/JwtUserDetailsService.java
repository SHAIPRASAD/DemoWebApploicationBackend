package com.shopping.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopping.demo.dao.User;

@Service
public class JwtUserDetailsService {
	
	@Autowired
	private UserService userService;
	
	public User loadUserByUsername(String username,String password) throws Exception {
		User user=userService.findByName(username);
		if(user==null || !user.getPassword().equals(password)) {
			throw new Exception("User not found");
		}
		return user;
	}

}
