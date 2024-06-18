package com.shopping.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopping.demo.dao.User;
import com.shopping.demo.model.SignUpRequestDTO;
import com.shopping.demo.repo.UserRepository;

@Service
public class UserService {
	
	@Autowired
    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    public User saveUser(SignUpRequestDTO request) {
    	User user = new User();
    	if(request!=null && !request.getUsername().isEmpty() && !request.getPassword().isEmpty() && !request.getType().isEmpty()) {
    		user.setName(request.getUsername());
    		user.setPassword(request.getPassword());
    		user.setType(request.getType());
    		user.setEmail(request.getEmail());
    	}
        return userRepository.save(user);
    }

    public void deleteUserById(String id) {
        userRepository.deleteById(id);
    }
    
    public User findByName(String name) {
        return userRepository.findByUsername(name);
    }
}

