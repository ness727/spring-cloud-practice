package com.megamaker.orderservice.mapper;

import com.megamaker.orderservice.dto.OrderDto;
import com.megamaker.orderservice.jpa.OrderEntity;
import com.megamaker.orderservice.vo.RequestOrder;
import com.megamaker.orderservice.vo.ResponseOrder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    OrderDto toOrderDto(OrderEntity orderEntity);

    OrderDto toOrderDto(RequestOrder requestOrder);

    OrderEntity toOrderEntity(OrderDto orderDto);

    ResponseOrder toResponseOrder(OrderDto orderDto);

    ResponseOrder toResponseOrder(OrderEntity orderEntity);
}
