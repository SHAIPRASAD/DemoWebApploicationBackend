package com.shopping.demo.controllerTest;

import com.shopping.demo.controller.ProductController;
import com.shopping.demo.dao.Product;
import com.shopping.demo.dao.User;
import com.shopping.demo.model.ResponseDTO;
import com.shopping.demo.service.ProductService;
import com.shopping.demo.service.UserService;
import com.shopping.demo.utils.FileUploadUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    @Mock
    private UserService userService;

    @Mock
    private FileUploadUtils fileUploadUtils;

    private MultipartFile[] files;
    private MultipartFile[] files1;
    private User user;
    private Product product;
    private Product product1;
    private List<Product> productList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        files = new MultipartFile[]{new MockMultipartFile("file1.jpg", "file1.jpg", "image/jpeg", new byte[] {})};
        files1 = new MultipartFile[]{new MockMultipartFile("file1.pdf", new byte[] {})};
        user  = new User("1","validUsername","email@gmail.com","validPassword","seller");
        product = new Product("1", "Product 1", 10L, "1", new ArrayList<String>());
        productList = new ArrayList<>();
        productList.add(product);
        product1=null;
    }

    @Test
    void testAddProduct() throws Exception {
        when(userService.findByName("validUsername")).thenReturn(user);
        when(fileUploadUtils.uploadProduct("Product 1", "10", user, files)).thenReturn(product);

        ResponseDTO response = productController.add("Product 1", "10", "validUsername",files);
        
        System.out.println(response.toString());
        assertEquals(200, response.getCode());
        assertEquals("Product added", response.getMessage());
        assertEquals("Success", response.getStatus());
    }
    
    @Test
    void testAddProductInvalidFormat() throws Exception {
        when(userService.findByName("validUsername")).thenReturn(user);
        when(fileUploadUtils.uploadProduct("Product 1", "10", user, files1)).thenReturn(product);

        ResponseDTO response = productController.add("Product 1", "10", "validUsername",files1);
        
        assertEquals(400, response.getCode());
    }

    @Test
    void testUpdateProduct() {
        when(productService.saveProduct(product)).thenReturn(product);

        ResponseDTO response = productController.update(product);

        assertEquals(200, response.getCode());
        assertEquals("Product updated", response.getMessage());
        assertEquals("Success", response.getStatus());
    }
    
    @Test
    void testUpdateProduct_InvalidRequest() {

        ResponseDTO response = productController.update(product1);

        assertEquals(400, response.getCode());
    }

    @Test
    void testGetProductById() {
        when(productService.getProductById("1")).thenReturn(product);

        ResponseDTO response = productController.get("1");

        assertEquals(200, response.getCode());
        assertEquals("Product fetched", response.getMessage());
        assertEquals("Success", response.getStatus());
        assertEquals(product, response.getData());
    }
    
    @Test
    void testGetProductById_InvalidRequest() {

        ResponseDTO response = productController.get("");

        assertEquals(400, response.getCode());
    }

    @Test
    void testGetAllProducts() {
        when(productService.getAllProduct()).thenReturn(productList);

        ResponseDTO response = productController.getAll();

        assertEquals(200, response.getCode());
        assertEquals("Products detail fetched", response.getMessage());
        assertEquals("Success", response.getStatus());
        assertEquals(productList, response.getData());
    }

    @Test
    void testDeleteProductById() {
        ResponseDTO response = productController.delete("1");

        assertEquals(200, response.getCode());
        assertEquals("Product deleted", response.getMessage());
        assertEquals("Success", response.getStatus());
        verify(productService, times(1)).deleteProductById("1");
    }
    
    @Test
    void testDeleteProductById_InvalidRequest() {
        ResponseDTO response = productController.delete("");

        assertEquals(400, response.getCode());
    }
}
