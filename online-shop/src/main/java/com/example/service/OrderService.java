package com.example.service;

import com.example.model.Order;

public interface OrderService {

    Order createOrder(Long userId);
    Order save(Order order);

}
