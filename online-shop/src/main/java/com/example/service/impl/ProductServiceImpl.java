package com.example.service.impl;

import com.example.model.Product;
import com.example.model.dto.ProductDTO;
import com.example.model.enums.ProductStatus;
import com.example.repository.ProductRepository;
import com.example.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final static String PRODUCT_NOT_FOUND = "Product not found!";

    private final ProductRepository productRepository;


    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;

    }

    @Override
    public Product add(ProductDTO productDTO) {
        return productRepository.save(Product.builder()
                .productName(productDTO.getProductName())
                .price(productDTO.getPrice())
                .productDescription(productDTO.getProductDescription())
                .productStatus(ProductStatus.AVAILABLE)
                .build());
    }

    @Override
    public Product findById(Long productId) {
        return productRepository.findById(productId).orElseThrow(()-> new RuntimeException(PRODUCT_NOT_FOUND));
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product updateById(ProductDTO productDTO, Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(()-> new RuntimeException(PRODUCT_NOT_FOUND));

        if (productDTO.getProductName() != null){
            product.setProductName(productDTO.getProductName());
        }
        if (productDTO.getProductDescription() != null){
            product.setProductDescription(productDTO.getProductDescription());
        }
        if (productDTO.getPrice() != null){
            product.setPrice(product.getPrice());
        }
        if (productDTO.getStatus() != null){
            product.setProductStatus(productDTO.getStatus());
        }

        return productRepository.save(product);
    }

    @Override
    public void deleteById(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(()-> new RuntimeException(PRODUCT_NOT_FOUND));
        product.setProductStatus(ProductStatus.UNAVAILABLE);
        productRepository.save(product);
    }

}

