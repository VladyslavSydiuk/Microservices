package com.example.controller;

import com.example.service.OrderedProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ordered-products")
public class OrderedProductController {

    private final OrderedProductService orderedProductService;

    public OrderedProductController(OrderedProductService orderedProductService) {
        this.orderedProductService = orderedProductService;
    }

    @DeleteMapping("/{userId}/{productId}")
    public ResponseEntity<Void> deleteProductFromOrder(
            @PathVariable Long userId,
            @PathVariable Long productId) {

        orderedProductService.deleteProductFromOrderByProductId(userId,productId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{userId}/{productId}/{amount}")
    public ResponseEntity<Void> editAmountOfProduct(
            @PathVariable Long userId,
            @PathVariable Long productId,
            @PathVariable Integer amount) {

        orderedProductService.editAmountOfProduct(userId,productId,amount);
        return ResponseEntity.ok().build();
    }
}