package com.example.service.impl;

import com.example.model.Order;
import com.example.model.enums.OrderStatus;
import com.example.repository.OrderRepository;
import com.example.repository.UserRepository;
import com.example.service.OrderService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Order createOrder(Long userId) {

        return orderRepository.findByUserIdAndOrderStatus(userId, OrderStatus.RECEIVED)
                .orElseGet(() -> orderRepository.save(
                        Order.builder()
                                .orderDate(Instant.now())
                                .orderStatus(OrderStatus.RECEIVED)
                                .orderedProducts(new ArrayList<>())
                                .address("Please add your address")
                                .orderDescription("Please write description")
                                .user(userRepository.findById(userId).get())
                                .build()
                ));

    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }


}
