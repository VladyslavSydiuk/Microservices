package com.example.service.facade;

import com.example.model.Order;
import com.example.model.dto.OrderDTO;

public interface OrderFacade {
    Order addProductsToOrder(OrderDTO orderDTO, Long userId);
}
