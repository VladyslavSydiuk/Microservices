package com.example.repository;

import com.example.model.OrderedProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderedProductRepository extends JpaRepository<OrderedProduct,Long> {
    boolean existsByProductIdAndOrderId(Long productId, Long orderId);
    Optional<OrderedProduct> findByProductIdAndOrderId(Long productId, Long orderId);
    void deleteByOrderIdAndProductId(Long orderId, Long productId);
}

