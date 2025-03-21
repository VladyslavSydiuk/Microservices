package com.example.service;

import com.example.model.Product;
import com.example.model.dto.ProductDTO;
import com.example.repository.ProductRepository;

public interface ProductService {

    Product addProduct(ProductDTO productDTO);
    Product getProductById(Long productId);
    Product updateProductById(ProductDTO productDTO, Long productId);
    void deleteProductById(Long productId);
}
