package com.shopping.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.shopping.demo.dao.Cart;
import com.shopping.demo.model.ResponseDTO;
import com.shopping.demo.service.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResponseDTO add(@RequestBody Cart cart) {
	ResponseDTO response=new ResponseDTO();
	cartService.saveCart(cart);
	
			return response;
	}
	
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public ResponseDTO get(@PathVariable(value="id")String id){
		ResponseDTO response=new ResponseDTO();
		cartService.getCartById(id);
				return response;
		}
	
	@RequestMapping(value = "/get/all", method = RequestMethod.GET)
	public ResponseDTO getAll(){
		ResponseDTO response=new ResponseDTO();
		cartService.getAllCarts();
				return response;
		}
	
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public ResponseDTO delete(@RequestBody Cart cart){
		ResponseDTO response=new ResponseDTO();
		cartService.deleteCartById(cart.getId());
				return response;
		}

}
