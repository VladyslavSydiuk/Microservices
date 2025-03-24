package com.example.service;

import com.example.model.Product;
import com.example.model.dto.ProductDTO;
import java.util.List;

public interface ProductService {

    Product add(ProductDTO productDTO);
    Product findById(Long productId);
    List<Product> findAll();
    Product updateById(ProductDTO productDTO, Long productId);
    void deleteById(Long productId);
}
