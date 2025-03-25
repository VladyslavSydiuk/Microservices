package com.example.service;

import com.example.model.OrderedProduct;

public interface OrderedProductService {
   boolean existsByProductIdAndOrderId(Long productId, Long orderId);

   OrderedProduct findByProductIdAndOrderId(Long productId, Long id);
   void deleteProductFromOrderByProductId(Long userId, Long productId);

   OrderedProduct editAmountOfProduct(Long userId, Long productId, Integer amount);
}
