package com.example.repository;

import com.example.model.Order;
import com.example.model.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    Optional<Order> findByUserId(Long userId);
    Optional<List<Order>> findAllByUserId(Long userId);
    Optional<Order> findByUserIdAndOrderStatus(Long userId, OrderStatus orderStatus);
    boolean existsByUserIdAndOrderStatus(Long userId, OrderStatus orderStatus);
    void deleteByUserIdAndOrderStatus(Long userId, OrderStatus orderStatus);

}
