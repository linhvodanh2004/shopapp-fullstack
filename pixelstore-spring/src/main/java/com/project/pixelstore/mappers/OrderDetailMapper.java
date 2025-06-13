package com.project.pixelstore.mappers;

import com.project.pixelstore.dtos.OrderDetailDTO;
import com.project.pixelstore.models.OrderDetail;
import com.project.pixelstore.responses.OrderDetailResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OrderDetailMapper {
    OrderDetail toOrderDetail(OrderDetailDTO orderDetailDTO);
    @Mapping(source = "order.id", target = "orderId")
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "id", target = "Id")
    OrderDetailResponse toOrderDetailResponse(OrderDetail orderDetail);

    void updateOrderDetail(@MappingTarget OrderDetail orderDetail, OrderDetailDTO orderDetailDTO);
}
