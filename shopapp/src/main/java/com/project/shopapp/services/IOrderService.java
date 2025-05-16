package com.project.shopapp.services;

import com.project.shopapp.dtos.OrderDTO;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.responses.OrderResponse;

import java.util.List;

public interface IOrderService {
    public OrderResponse createOrder(OrderDTO orderDTO ) throws DataNotFoundException;
    public OrderResponse getOrderById(Long id) throws DataNotFoundException;
    public OrderResponse getAllOrders();
    public OrderResponse updateOrder(Long orderId, OrderDTO orderDTO) throws DataNotFoundException;
    public void deleteOrder(Long id);
    public List<OrderResponse> getOrdersByUserId(Long userId);
}
