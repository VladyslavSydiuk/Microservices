package com.example.controller;

import com.example.model.Product;
import com.example.model.dto.ProductDTO;
import com.example.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAll() {
        return ResponseEntity.ok(productService.findAll());
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



}
