package com.project.shopapp.mappers;

import com.project.shopapp.dtos.OrderDTO;
import com.project.shopapp.models.Order;
import com.project.shopapp.responses.OrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    Order toOrder(OrderDTO orderDTO);
    OrderResponse toOrderResponse(Order order);
    void updateOrder(@MappingTarget Order order, OrderDTO orderDTO);
}
