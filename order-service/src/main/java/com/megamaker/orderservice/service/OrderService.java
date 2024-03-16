package com.megamaker.orderservice.service;

import com.megamaker.orderservice.dto.OrderDto;
import com.megamaker.orderservice.jpa.OrderEntity;

public interface OrderService {
    OrderDto createOrder(OrderDto orderDto);

    OrderDto getOrderByOrderId(String orderId);

    Iterable<OrderEntity> getOrdersByUserId(String userId);
}
