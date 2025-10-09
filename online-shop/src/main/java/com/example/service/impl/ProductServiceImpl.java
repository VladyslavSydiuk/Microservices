package com.example.service.impl;

import com.example.model.Product;
import com.example.model.dto.ProductDTO;
import com.example.model.enums.ProductStatus;
import com.example.repository.ProductRepository;
import com.example.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
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
                .stock(0)
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
    public Page<Product> findAll(int page, int size, String categoryName) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("category.name").ascending());
        log.info("categoryName: {}", categoryName);
        log.info("pageable: {}", pageable);
        if ("all".equalsIgnoreCase(categoryName) || categoryName == null || categoryName.trim().isEmpty()) {
            return productRepository.findAll(pageable);
        } else {
            return productRepository.findByCategoryName(categoryName, pageable);
        }
    }

    @Override
    public Page<Product> findAll(int page, int size, String categoryName, String searchTerm) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("productName").ascending());
        log.info("categoryName: {}, searchTerm: {}", categoryName, searchTerm);
        log.info("pageable: {}", pageable);
        
        // Якщо пошуковий запит пустий, повертаємо звичайні результати
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return findAll(page, size, categoryName);
        }
        
        // Пошук товарів за назвою з врахуванням категорії
        if ("all".equalsIgnoreCase(categoryName) || categoryName == null || categoryName.trim().isEmpty()) {
            return productRepository.findByProductNameContainingIgnoreCase(searchTerm, pageable);
        } else {
            return productRepository.findByCategoryNameAndProductNameContainingIgnoreCase(categoryName, searchTerm, pageable);
        }
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
            product.setPrice(productDTO.getPrice());
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

    @Override
    public Product getByProductName(String productName) {
        return productRepository.findByProductName(productName);
    }

    @Override
    @Transactional
    public Product updateStock(Long productId, int stock) {
        if (stock < 0) stock = 0;
        Product p = productRepository.findById(productId).orElseThrow(() -> new RuntimeException(PRODUCT_NOT_FOUND));
        p.setStock(stock);
        // auto-status
        if (p.getStock() == null || p.getStock() <= 0) {
            p.setProductStatus(ProductStatus.UNAVAILABLE);
        } else if (p.getProductStatus() == null || p.getProductStatus() == ProductStatus.UNAVAILABLE) {
            p.setProductStatus(ProductStatus.AVAILABLE);
        }
        return productRepository.save(p);
    }

    @Override
    @Transactional
    public Product adjustStock(Long productId, int delta) {
        Product p = productRepository.findById(productId).orElseThrow(() -> new RuntimeException(PRODUCT_NOT_FOUND));
        int current = p.getStock() == null ? 0 : p.getStock();
        long next = (long) current + (long) delta;
        int clamped = (int) Math.max(0, Math.min(Integer.MAX_VALUE, next));
        p.setStock(clamped);
        // auto-status
        if (p.getStock() == null || p.getStock() <= 0) {
            p.setProductStatus(ProductStatus.UNAVAILABLE);
        } else if (p.getProductStatus() == null || p.getProductStatus() == ProductStatus.UNAVAILABLE) {
            p.setProductStatus(ProductStatus.AVAILABLE);
        }
        return productRepository.save(p);
    }

}
