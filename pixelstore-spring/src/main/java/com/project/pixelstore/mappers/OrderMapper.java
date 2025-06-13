package com.project.pixelstore.mappers;

import com.project.pixelstore.dtos.OrderDTO;
import com.project.pixelstore.models.Order;
import com.project.pixelstore.responses.OrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    Order toOrder(OrderDTO orderDTO);
    @Mapping(source = "user.id", target = "userId")
    OrderResponse toOrderResponse(Order order);
    void updateOrder(@MappingTarget Order order, OrderDTO orderDTO);
}
