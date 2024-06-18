package com.shopping.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.shopping.demo.dao.Product;
import com.shopping.demo.dao.User;
import com.shopping.demo.model.ResponseDTO;
import com.shopping.demo.service.ProductService;
import com.shopping.demo.service.UserService;
import com.shopping.demo.utils.FileUploadUtils;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	private UserService userRepository;

	@RequestMapping(value = "/add", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseDTO add(@RequestParam(value = "name") String name, @RequestParam(value = "price") String price,
			@RequestParam(value = "seller") String seller,
			@RequestParam(value = "files", required = false) MultipartFile[] files) {
		ResponseDTO response = new ResponseDTO();
		try {
			if (!name.isBlank() && !price.isBlank() && !seller.isBlank()) {
				FileUploadUtils fileUploadUtils = new FileUploadUtils();
				User user = userRepository.findByName(seller);
				Product product = fileUploadUtils.uploadProduct(name, price, user, files);
				productService.saveProduct(product);
				response.setCode(200);
				response.setMessage("Product added");
				response.setStatus("Success");
			}
		} catch (Exception e) {
			response.setCode(e.getMessage().contains("server") ? 500 : 400);
			response.setMessage(e.getMessage());
			response.setStatus("Failure");
		}

		return response;
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public ResponseDTO update(@RequestBody Product product) {
		ResponseDTO response = new ResponseDTO();
		if(product!=null) {
		Product updatedProduct = productService.saveProduct(product);
		if (updatedProduct != null) {
			response.setCode(200);
			response.setMessage("Product updated");
			response.setStatus("Success");
		} else {
			response.setCode(500);
			response.setMessage("Internal Server Error");
			response.setStatus("Failure");
		}
		}
		else
		{
			response.setCode(400);
			response.setMessage("Invalid Request");
			response.setStatus("Failure");
		}
		return response;
	}

	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public ResponseDTO get(@PathVariable(value = "id") String id) {
		ResponseDTO response = new ResponseDTO();
		Product product = new Product();
		if(id!=null && !id.isBlank()) {
		product = productService.getProductById(id);
		
		if (product != null) {
			response.setCode(200);
			response.setMessage("Product fetched");
			response.setStatus("Success");
			response.setData(product);
		} else {
			response.setCode(500);
			response.setMessage("Internal Server Error");
			response.setStatus("Failure");
		}
		}
		else
		{
			response.setCode(400);
			response.setMessage("Invalid Request");
			response.setStatus("Failure");
		}
		return response;
	}

	@RequestMapping(value = "/get/all", method = RequestMethod.GET)
	public ResponseDTO getAll() {
		ResponseDTO response = new ResponseDTO();
		try {
			List<Product> products = productService.getAllProduct();
			response.setCode(200);
			response.setMessage("Products detail fetched");
			response.setStatus("Success");
			response.setData(products);
		} catch (Exception e) {
			response.setCode(500);
			response.setMessage("Internal Server Error");
			response.setStatus("Failure");
		}
		return response;
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public ResponseDTO delete(@PathVariable(value = "id") String id) {
		ResponseDTO response = new ResponseDTO();
		try {
			if(id!=null && !id.isBlank()) {
			productService.deleteProductById(id);
			response.setCode(200);
			response.setMessage("Product deleted");
			response.setStatus("Success");
			}
			else {
				response.setCode(400);
				response.setMessage("Invalid Request");
				response.setStatus("Failure");
			}
		} catch (Exception e) {
			response.setCode(500);
			response.setMessage("Internal Server Error");
			response.setStatus("Failure");
		}
		return response;
	}

}
