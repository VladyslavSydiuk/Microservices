package com.example.service.impl;

import com.example.model.Order;
import com.example.model.OrderedProduct;
import com.example.model.enums.OrderStatus;
import com.example.repository.OrderRepository;
import com.example.repository.OrderedProductRepository;
import com.example.service.OrderedProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderedProductServiceImpl implements OrderedProductService {

    private final OrderedProductRepository orderedProductRepository;
    private final OrderRepository orderRepository;

    public OrderedProductServiceImpl(OrderedProductRepository orderedProductRepository, OrderRepository orderRepository) {
        this.orderedProductRepository = orderedProductRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public boolean existsByProductIdAndOrderId(Long productId, Long orderId) {
        return orderedProductRepository.existsByProductIdAndOrderId(productId, orderId);
    }

    @Override
    public OrderedProduct findByProductIdAndOrderId(Long productId, Long orderId) {
        return orderedProductRepository.findByProductIdAndOrderId(productId, orderId)
                .orElseThrow(() -> new RuntimeException("Ordered product not found!"));
    }

    @Override
    @Transactional
    public void deleteProductFromOrderByProductId(Long userId, Long productId) {
        Order order = orderRepository.findByUserIdAndOrderStatus(userId, OrderStatus.ACTIVE)
                .orElseThrow(()-> new RuntimeException("Order not found!"));
        orderedProductRepository.deleteByOrderIdAndProductId(order.getId(), productId);
    }

    @Override
    public OrderedProduct editAmountOfProduct(Long userId, Long productId, Integer amount) {
        Order order = orderRepository.findByUserIdAndOrderStatus(userId, OrderStatus.ACTIVE)
                .orElseThrow(()-> new RuntimeException("Order not found!"));
        OrderedProduct orderedProduct = orderedProductRepository.findByProductIdAndOrderId(productId, order.getId())
                .orElseThrow(() -> new RuntimeException("OrderedProduct not found!"));
        orderedProduct.setAmount(amount);
        return orderedProductRepository.save(orderedProduct);
    }

}