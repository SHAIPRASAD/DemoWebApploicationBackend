package com.shopping.demo.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopping.demo.dao.Product;
import com.shopping.demo.dao.User;
import com.shopping.demo.repo.UserRepository;

public class FileUploadUtils {

	private static final String UPLOAD_DIRECTORY = "C:\\Users\\Lenovo\\Downloads\\demo\\demo\\filesystem\\";

	private long maxFileSize=10485760;

	private String[] allowedFileTypes= {"jpg","png","jpeg"};

	public Product uploadProduct(String name, String price, User seller, MultipartFile[] files) throws Exception {
		System.out.println("inside util");
		Product product = new Product();
		if(seller!=null) {
		product.setName(name);
		product.setPrice(Long.valueOf(price));
		product.setSeller(seller.getId());
		List<String> filePaths = new ArrayList<>();
		if(files!=null && files.length>0) {
		for (MultipartFile file : files) {
			 if (file.getSize() > maxFileSize) {
				 throw new Exception(file.getOriginalFilename()+" file size exceeded");
			 }
			 String fileExtension = getFileExtension(file);
             if (!Arrays.asList(allowedFileTypes).contains(fileExtension.toLowerCase())) {
            	 throw new Exception(file.getOriginalFilename()+" has inavlid format of file");
             }
			String fileName = storeFile(file);
			filePaths.add(fileName);
		}
		}
		
		product.setImages(filePaths);
		}
		else {
			throw new Exception("User not found");
		}
		return product;
	}


	private String getFileExtension(MultipartFile file) {
		 String originalFileName = file.getOriginalFilename();
	        return originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
	}

	private String storeFile(MultipartFile file) throws Exception {
		ObjectMapper obj = new ObjectMapper();
		try {
		String fileName = obj.writeValueAsString(System.currentTimeMillis());
        String filePath = UPLOAD_DIRECTORY + fileName + "." + getFileExtension(file);
        File dest = new File(filePath);
        file.transferTo(dest);
        return filePath;
		}catch(Exception e){
			throw new Exception("Internal Server Error");
		}
	}

}
