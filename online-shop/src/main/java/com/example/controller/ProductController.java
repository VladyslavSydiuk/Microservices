package com.example.controller;

import com.example.model.Product;
import com.example.model.dto.ProductDTO;
import com.example.model.dto.CreateReviewDTO;
import com.example.model.dto.ReviewDTO;
import com.example.service.ProductService;
import com.example.service.ReviewService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PatchMapping;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping({"/products", "/api/products"})
public class ProductController {

    private final ProductService productService;
    private final ReviewService reviewService;

    public ProductController(ProductService productService, ReviewService reviewService) {
        this.productService = productService;
        this.reviewService = reviewService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAll() {
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<Product>> getByProductName(@PathVariable String name) {
        List<Product> list = new ArrayList<>();
        list.add(productService.getByProductName(name));
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getById(@PathVariable("productId") Long productId) {
        return ResponseEntity.ok(productService.findById(productId));
    }
    @PostMapping
    public ResponseEntity<Product> add(@RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(productService.add(productDTO));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Product> update(@RequestBody ProductDTO productDTO, @PathVariable("productId") Long productId) {
        return ResponseEntity.ok(productService.updateById(productDTO, productId));
    }
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> delete(@PathVariable("productId") Long productId){
        productService.deleteById(productId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Page<Product>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "all") String categoryName,
            @RequestParam(required = false) String searchTerm) {
        
        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            return ResponseEntity.ok(productService.findAll(page, size, categoryName, searchTerm));
        }
        
        return ResponseEntity.ok(productService.findAll(page, size, categoryName));
    }

    @GetMapping("/{productId}/reviews")
    public ResponseEntity<List<ReviewDTO>> getReviews(@PathVariable("productId") Long productId) {
        return ResponseEntity.ok(reviewService.getReviews(productId));
    }

    @PostMapping("/{productId}/reviews")
    public ResponseEntity<ReviewDTO> addReview(
            @PathVariable("productId") Long productId,
            @RequestBody CreateReviewDTO body,
            @RequestHeader(value = "X-User-Name", required = false) String userNameOpt) {
        return ResponseEntity.status(201).body(reviewService.addReview(productId, body, userNameOpt));
    }

    public static class StockDTO {
        public Integer stock;
    }
    public static class AdjustStockDTO {
        public Integer delta;
    }

    @GetMapping("/{productId}/stock")
    public ResponseEntity<StockDTO> getStock(@PathVariable Long productId) {
        Product p = productService.findById(productId);
        StockDTO dto = new StockDTO();
        dto.stock = p.getStock();
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{productId}/stock")
    public ResponseEntity<StockDTO> setStock(@PathVariable Long productId, @RequestBody StockDTO body) {
        if (body == null || body.stock == null) return ResponseEntity.badRequest().build();
        Product updated = productService.updateStock(productId, body.stock);
        StockDTO dto = new StockDTO();
        dto.stock = updated.getStock();
        return ResponseEntity.ok(dto);
    }

    @PatchMapping("/{productId}/stock")
    public ResponseEntity<StockDTO> adjustStock(@PathVariable Long productId, @RequestBody AdjustStockDTO body) {
        if (body == null || body.delta == null) return ResponseEntity.badRequest().build();
        Product updated = productService.adjustStock(productId, body.delta);
        StockDTO dto = new StockDTO();
        dto.stock = updated.getStock();
        return ResponseEntity.ok(dto);
    }
}
