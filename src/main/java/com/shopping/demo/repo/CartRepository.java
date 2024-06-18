package com.shopping.demo.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.shopping.demo.dao.Cart;

public interface CartRepository extends MongoRepository<Cart, String> {

}
