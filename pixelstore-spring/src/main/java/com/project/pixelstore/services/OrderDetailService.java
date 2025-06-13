package com.project.pixelstore.services;

import com.project.pixelstore.dtos.OrderDetailDTO;
import com.project.pixelstore.exceptions.DataNotFoundException;
import com.project.pixelstore.models.OrderDetail;

import java.util.List;

public interface OrderDetailService {
    OrderDetail createOrderDetail(OrderDetailDTO orderDetailDTO) throws DataNotFoundException;
    OrderDetail getOrderDetailById(Long id) throws DataNotFoundException;
    void deleteOrderDetail(Long id);
    OrderDetail updateOrderDetail(Long id, OrderDetailDTO orderDetailDTO) throws DataNotFoundException;
    List<OrderDetail> getOrderDetailsByOrderId(Long orderId);
}
