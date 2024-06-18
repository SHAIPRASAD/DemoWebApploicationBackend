package com.shopping.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.shopping.demo.dao.User;
import com.shopping.demo.model.LoginRequest;
import com.shopping.demo.model.ResponseDTO;
import com.shopping.demo.service.JwtUserDetailsService;


@RestController
public class LoginController {
	
//	@Autowired
//	private JwtTokenUtils jwtTokenUtil;
	
	@Autowired
	private JwtUserDetailsService userDetailsService;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseDTO createAuthenticationToken(@RequestBody LoginRequest authenticationRequest) {
		ResponseDTO response=new ResponseDTO();
		try {
//		inituc();
//		initac();
//		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		User userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername(),authenticationRequest.getPassword());
		response.setCode(200);
		response.setMessage("User logged in");
		response.setStatus("Success");
//		final String token = jwtTokenUtil.generateToken(userDetails, true);
		}catch(Exception e) {
			response.setCode(400);
			response.setMessage("Invalid credantials");
			response.setStatus("Failure");
		}
		return response;
		
	}
}
