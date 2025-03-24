package com.example.controller;

import com.example.model.Order;
import com.example.model.Product;
import com.example.model.dto.OrderDTO;
import com.example.model.dto.OrderedProductDTO;
import com.example.model.dto.ProductDTO;
import com.example.service.OrderService;
import com.example.service.facade.OrderFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderFacade orderFacade;
    private final OrderService orderService;


    public OrderController(OrderFacade orderFacade, OrderService orderService) {
        this.orderFacade = orderFacade;
        this.orderService = orderService;
    }

    @PostMapping("/{userId}")
    public ResponseEntity<Order> addProductsToOrder(@RequestBody OrderDTO orderDTO, @PathVariable("userId") Long userId) {
        return ResponseEntity.ok(orderFacade.addProductsToOrder(orderDTO,userId));
    }
}
