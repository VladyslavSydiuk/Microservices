package com.example.controller;

import com.example.model.UserPrincipal;
import com.example.service.OrderedProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProductFromOrder(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long productId) {

        orderedProductService.deleteProductFromOrderByProductId(
                userPrincipal.getId(),
                productId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{productId}/{amount}")
    public ResponseEntity<Void> editAmountOfProduct(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long productId,
            @PathVariable Integer amount) {

        orderedProductService.editAmountOfProduct(userPrincipal.getId(),productId,amount);
        return ResponseEntity.ok().build();
    }
}