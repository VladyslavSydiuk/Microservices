package com.example.controller;

import com.example.model.Order;
import com.example.model.dto.OrderDTO;
import com.example.model.dto.OrderInfoDTO;
import com.example.service.OrderService;
import com.example.service.facade.OrderFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderFacade orderFacade;
    private final OrderService orderService;


    public OrderController(OrderFacade orderFacade, OrderService orderService) {
        this.orderFacade = orderFacade;
        this.orderService = orderService;
    }

    @GetMapping("/active/{userId}")
    public ResponseEntity<Order> getActiveOrder(@PathVariable Long userId) {
        return ResponseEntity.ok(orderService.getActiveOrder(userId));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Order>> getAll(@PathVariable Long userId) {
        return ResponseEntity.ok(orderService.getAll(userId));
    }

    @PostMapping("/{userId}")
    public ResponseEntity<Order> addProductsToOrder(@RequestBody OrderDTO orderDTO, @PathVariable("userId") Long userId) {
        return ResponseEntity.ok(orderFacade.addProductsToOrder(orderDTO,userId));
    }
    @PatchMapping("/{userId}")
    public ResponseEntity<Order> acceptOrder(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(orderService.acceptOrder(userId));
    }

    @PutMapping({"/{userId}"})
    public ResponseEntity<Void> setInfo(@RequestBody OrderInfoDTO orderInfoDTO, @PathVariable Long userId) {
        orderService.setInfo(orderInfoDTO, userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping()
    public ResponseEntity<Void> cancelActiveOrder(@PathVariable("userId") Long userId) {
        orderService.cancelActiveOrder(userId);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrderById(@PathVariable("orderId") Long orderId) {
        orderService.deleteOrderById(orderId);
        return ResponseEntity.ok().build();
    }

}
