package com.megamaker.orderservice.service;

import com.megamaker.orderservice.dto.OrderDto;
import com.megamaker.orderservice.jpa.OrderEntity;
import com.megamaker.orderservice.jpa.OrderRepository;
import com.megamaker.orderservice.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        orderDto.setOrderId(UUID.randomUUID().toString());
        orderDto.setTotalPrice(orderDto.getQty() * orderDto.getUnitPrice());

        OrderEntity userEntity = OrderMapper.INSTANCE.toOrderEntity(orderDto);
        orderRepository.save(userEntity);

        return orderDto;
    }

    @Override
    public OrderDto getOrderByOrderId(String orderId) {
        OrderEntity orderEntity = orderRepository.findByOrderId(orderId);
        return OrderMapper.INSTANCE.toOrderDto(orderEntity);
    }

    @Override
    public Iterable<OrderEntity> getOrdersByUserId(String userId) {
        return orderRepository.findByUserId(userId);
    }
}
