package com.example.service;

import com.example.model.Order;
import com.example.model.dto.OrderInfoDTO;

import java.util.List;

public interface OrderService {

    Order getActiveOrder(Long userId);
    List<Order> getAll(Long userId);
    Order createOrder(Long userId);
    Order save(Order order);
    void cancelActiveOrder(Long userId);
    Order confirmOrder(Long userId);
    void deleteOrderById(Long orderId);
    Order setInfo(OrderInfoDTO orderInfoDTO, Long userId);
}