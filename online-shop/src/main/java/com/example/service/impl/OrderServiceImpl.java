package com.example.service.impl;

import com.example.model.Order;
import com.example.model.dto.OrderInfoDTO;
import com.example.model.enums.OrderStatus;
import com.example.repository.OrderRepository;
import com.example.repository.UserRepository;
import com.example.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

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

        return orderRepository.findByUserIdAndOrderStatus(userId, OrderStatus.ACTIVE)
                .orElseGet(() -> orderRepository.save(
                        Order.builder()
                                .orderDate(Instant.now())
                                .orderStatus(OrderStatus.ACTIVE)
                                .orderedProducts(new ArrayList<>())
                                .address("Please add your address")
                                .orderDescription("Please write description")
                                .user(userRepository.findById(userId).orElseThrow(()-> new RuntimeException("User not found")))
                                .build()
                ));
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public void cancelActiveOrder(Long userId) {

        orderRepository.deleteByUserIdAndOrderStatus(userId, OrderStatus.ACTIVE);

    }

    @Override
    public Order confirmOrder(Long userId) {
        Order order = orderRepository.findByUserIdAndOrderStatus(userId, OrderStatus.ACTIVE).orElseThrow(() -> new RuntimeException("Order not found"));
        order.setOrderStatus(OrderStatus.CONFIRMED);
        return orderRepository.save(order);
    }

    @Override
    public void deleteOrderById(Long orderId) {
        orderRepository.deleteById(orderId);
    }

    @Override
    public Order setInfo(OrderInfoDTO orderInfoDTO, Long userId) {
        Order order = orderRepository.findByUserIdAndOrderStatus(userId, OrderStatus.ACTIVE).orElseThrow(() -> new RuntimeException("Order not found!"));
        order.setOrderDescription(orderInfoDTO.getOrderDescription());
        order.setAddress(orderInfoDTO.getAddress());
        return orderRepository.save(order);
    }

    @Override
    public Order getActiveOrder(Long userId) {
        return orderRepository.findByUserIdAndOrderStatus(userId, OrderStatus.ACTIVE).orElseThrow(() -> new RuntimeException("Order not found!"));
    }

    @Override
    public List<Order> getAll(Long userId) {
        return orderRepository.findAllByUserId(userId).orElseThrow(() -> new RuntimeException("Order not found!"));
    }

}
