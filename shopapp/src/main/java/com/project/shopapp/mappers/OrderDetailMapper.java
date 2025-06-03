package com.project.shopapp.mappers;

import com.project.shopapp.dtos.OrderDetailDTO;
import com.project.shopapp.models.OrderDetail;
import com.project.shopapp.responses.OrderDetailResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OrderDetailMapper {
    OrderDetail toOrderDetail(OrderDetailDTO orderDetailDTO);
    OrderDetailResponse toOrderDetailResponse(OrderDetail orderDetail);
    void updateOrderDetail(@MappingTarget OrderDetail orderDetail, OrderDetailDTO orderDetailDTO);
}
