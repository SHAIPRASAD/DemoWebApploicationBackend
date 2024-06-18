package com.shopping.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shopping.demo.dao.Product;
import com.shopping.demo.repo.ProductRepository;

@Service
public class ProductService {
	
	private final ProductRepository productRepository;


    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    public Product getProductById(String id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProductById(String id) {
    	productRepository.deleteById(id);
    }
    
//    public Product updateProduct(Product product) {
//        return productRepository.(product);
//    }

}
