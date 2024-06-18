package com.shopping.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.shopping.demo.dao.User;
import com.shopping.demo.model.ResponseDTO;
import com.shopping.demo.model.SignUpRequestDTO;
import com.shopping.demo.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/sign/up", method = RequestMethod.POST)
	public ResponseDTO add(@RequestBody SignUpRequestDTO user) throws Exception {
	ResponseDTO response=new ResponseDTO();
	if(user!=null &&user.getUsername()!=null && user.getPassword()!=null&&  user.getType()!=null && !user.getUsername().isEmpty() && !user.getPassword().isEmpty() && !user.getType().isEmpty()) {
		User persistedUser=userService.saveUser(user);
		response.setMessage("User added");
		response.setStatus("Success");
		response.setCode(200);
	}
	else {
		response.setMessage("Check the request");
		response.setStatus("Failure");
		response.setCode(400);
	}
			return response;
	}

}
