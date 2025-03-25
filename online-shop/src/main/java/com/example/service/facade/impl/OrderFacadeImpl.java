package com.example.service.facade.impl;

import com.example.model.Order;
import com.example.model.OrderedProduct;
import com.example.model.Product;
import com.example.model.dto.OrderDTO;
import com.example.model.dto.OrderedProductDTO;
import com.example.service.OrderService;
import com.example.service.OrderedProductService;
import com.example.service.ProductService;
import com.example.service.facade.OrderFacade;
import org.springframework.stereotype.Service;

@Service
public class OrderFacadeImpl implements OrderFacade {

    private final OrderService orderService;
    private final ProductService productService;
    private final OrderedProductService orderedProductService;

    public OrderFacadeImpl(OrderService orderService, ProductService productService, OrderedProductService orderedProductService) {
        this.orderService = orderService;
        this.productService = productService;
        this.orderedProductService = orderedProductService;

    }


    @Override
    public Order addProductsToOrder(OrderDTO orderDTO, Long userId) {
        Order order = orderService.createOrder(userId);

        for (OrderedProductDTO orderedProductDTO : orderDTO.getOrderedProducts()) {

            if (orderedProductService.existsByProductIdAndOrderId(orderedProductDTO.getProductId(), order.getId())) {
                OrderedProduct orderedProduct =
                        orderedProductService.findByProductIdAndOrderId(orderedProductDTO.getProductId(), order.getId());
                orderedProduct.setAmount(orderedProduct.getAmount() + orderedProductDTO.getAmount());
            } else {

                Product product = productService.findById(orderedProductDTO.getProductId());

                OrderedProduct orderedProduct = OrderedProduct.builder()
                        .product(product)
                        .price(product.getPrice())
                        .amount(orderedProductDTO.getAmount())
                        .order(order)
                        .build();

                order.getOrderedProducts().add(orderedProduct);
            }
        }

        return orderService.save(order);
    }
}
