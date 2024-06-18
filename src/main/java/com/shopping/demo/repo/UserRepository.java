package com.shopping.demo.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.shopping.demo.dao.User;

public interface UserRepository extends MongoRepository<User, String> {
    
	@Query("{ 'name' : ?0}")
	User findByUsername(String name);
}

