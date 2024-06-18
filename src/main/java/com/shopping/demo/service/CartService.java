package com.shopping.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shopping.demo.dao.Cart;
import com.shopping.demo.repo.CartRepository;

@Service
public class CartService {
	
	private final CartRepository cartRepository;


    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    public Cart getCartById(String id) {
        return cartRepository.findById(id).orElse(null);
    }

    public Cart saveCart(Cart cart) {
        return cartRepository.save(cart);
    }

    public void deleteCartById(String id) {
    	cartRepository.deleteById(id);
    }

}
