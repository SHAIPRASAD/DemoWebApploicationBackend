package com.shopping.demo.utilsTest;

import com.shopping.demo.dao.Product;
import com.shopping.demo.dao.User;
import com.shopping.demo.utils.FileUploadUtils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FileUploadUtilsTest {

    private FileUploadUtils fileUploadUtils;
    private User user;
    private MultipartFile[] files;
    private MockedStatic<System> mockedStatic;

    @BeforeEach
    void setUp() {
        fileUploadUtils = new FileUploadUtils();
        user = new User("1", "username",  "user@example.com","password", "seller");
        files = new MultipartFile[]{
                new MockMultipartFile("file1.jpg", "file1.jpg", "image/jpeg", new byte[]{1, 2, 3}),
                new MockMultipartFile("file2.png", "file2.png", "image/png", new byte[]{4, 5, 6})
        };
//        mockedStatic = Mockito.mockStatic(System.class);
    }

    @Test
    void testUploadProduct_ValidFiles() throws Exception {

        Product product = fileUploadUtils.uploadProduct("Product Name", "10", user, files);

        assertNotNull(product);
        assertEquals("Product Name", product.getName());
        assertEquals(10L, product.getPrice());
        assertEquals(user.getId(), product.getSeller());
        assertEquals(2, product.getImages().size());

        // Clean up the uploaded files
        for (String filePath : product.getImages()) {
            Files.delete(Paths.get(filePath));
        }
    }

    @Test
    void testUploadProduct_FileSizeExceeded() {
        MultipartFile[] filesWithLargeSize = new MultipartFile[]{
                new MockMultipartFile("file1.jpg", "file1.jpg", "image/jpeg", new byte[]{1, 2, 3})};
        }
}