package com.shopping.demo.serviceTest;

import com.shopping.demo.dao.Product;
import com.shopping.demo.dao.User;
import com.shopping.demo.repo.ProductRepository;
import com.shopping.demo.service.ProductService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    private Product product;
    private List<Product> productList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        User user  = new User("1","validUsername","email@gmail.com","validPassword","seller");
        product = new Product("1", "Product 1", 10L, "1", new ArrayList<String>());
        productList = new ArrayList<>();
        productList.add(product);
    }

    @Test
    void testGetAllProduct() {
        when(productRepository.findAll()).thenReturn(productList);

        List<Product> result = productService.getAllProduct();

        assertEquals(productList, result);
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testGetProductById() {
        when(productRepository.findById("1")).thenReturn(Optional.of(product));

        Product result = productService.getProductById("1");

        assertEquals(product, result);
        verify(productRepository, times(1)).findById("1");
    }

    @Test
    void testSaveProduct() {
        when(productRepository.save(product)).thenReturn(product);

        Product result = productService.saveProduct(product);

        assertEquals(product, result);
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void testDeleteProductById() {
        productService.deleteProductById("1");

        verify(productRepository, times(1)).deleteById("1");
    }
}
