package com.megamaker.orderservice.controller;

import com.megamaker.orderservice.dto.OrderDto;
import com.megamaker.orderservice.jpa.OrderEntity;
import com.megamaker.orderservice.mapper.OrderMapper;
import com.megamaker.orderservice.service.OrderService;
import com.megamaker.orderservice.vo.RequestOrder;
import com.megamaker.orderservice.vo.ResponseOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("order-service")
@RequiredArgsConstructor
public class OrderController {
    private final Environment env;
    private final OrderService orderService;

    @GetMapping("/health_check")
    public String status() {
        return String.format("It's working in Order Service on PORT %s", env.getProperty("local.server.port"));
    }

    @PostMapping("/{userId}/orders")
    public ResponseEntity<ResponseOrder> createOrder(@PathVariable("userId") String userId,
                                                     @RequestBody RequestOrder requestOrder) {
        OrderDto orderDto = OrderMapper.INSTANCE.toOrderDto(requestOrder);
        orderDto.setUserId(userId);
        orderService.createOrder(orderDto);

        ResponseOrder responseOrder = OrderMapper.INSTANCE.toResponseOrder(orderDto);
        return ResponseEntity.created(URI.create("test")).body(responseOrder);
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<ResponseOrder>> createOrder(@PathVariable("userId") String userId) {
        Iterable<OrderEntity> orders = orderService.getOrdersByUserId(userId);

        List<ResponseOrder> result = new ArrayList<>();
        orders.forEach(e -> result.add(OrderMapper.INSTANCE.toResponseOrder(e)));

        return ResponseEntity.ok().body(result);
    }
}
