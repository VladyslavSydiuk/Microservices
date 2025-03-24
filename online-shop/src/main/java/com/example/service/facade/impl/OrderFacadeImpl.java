package com.example.service.facade.impl;

import com.example.model.Order;
import com.example.model.OrderedProduct;
import com.example.model.Product;
import com.example.model.User;
import com.example.model.dto.OrderDTO;
import com.example.model.dto.OrderedProductDTO;
import com.example.service.OrderService;
import com.example.service.ProductService;
import com.example.service.UserService;
import com.example.service.facade.OrderFacade;
import org.springframework.stereotype.Service;

@Service
public class OrderFacadeImpl implements OrderFacade {

    private final OrderService orderService;
    private final ProductService productService;

    private final UserService userService;

    public OrderFacadeImpl(OrderService orderService, ProductService productService, UserService userService) {
        this.orderService = orderService;
        this.productService = productService;
        this.userService = userService;
    }


    @Override
    public Order addProductsToOrder(OrderDTO orderDTO, Long userId) {
        User user = userService.findById(userId);
        Order order = orderService.createOrder(userId);

        for (OrderedProductDTO orderedProductDTO : orderDTO.getOrderedProducts()) {
            Product product = productService.findById(orderedProductDTO.getProductId());

            OrderedProduct orderedProduct = OrderedProduct.builder()
                    .product(product)
                    .price(orderedProductDTO.getPrice())
                    .amount(orderedProductDTO.getAmount())
                    .order(order)
                    .build();

            order.getOrderedProducts().add(orderedProduct);
        }

        return orderService.save(order);
    }
}
