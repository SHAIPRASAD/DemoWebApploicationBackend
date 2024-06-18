package com.shopping.demo.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.shopping.demo.dao.Product;

public interface ProductRepository extends MongoRepository<Product, String> {

}
