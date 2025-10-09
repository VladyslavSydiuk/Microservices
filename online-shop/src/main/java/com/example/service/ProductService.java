package com.example.service;

import com.example.model.Product;
import com.example.model.dto.ProductDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    Product add(ProductDTO productDTO);
    Product findById(Long productId);
    List<Product> findAll();
    Product updateById(ProductDTO productDTO, Long productId);
    void deleteById(Long productId);
    Product getByProductName(String productName);
    Page<Product> findAll(int page, int size, String categoryName);
    Page<Product> findAll(int page, int size, String categoryName, String searchTerm);

    // New stock APIs
    Product updateStock(Long productId, int stock);   // set absolute value
    Product adjustStock(Long productId, int delta);   // add/subtract
}
