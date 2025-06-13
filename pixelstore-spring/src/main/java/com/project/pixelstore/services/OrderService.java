package com.project.pixelstore.services;

import com.project.pixelstore.dtos.OrderDTO;
import com.project.pixelstore.exceptions.DataNotFoundException;
import com.project.pixelstore.models.Order;
import com.project.pixelstore.responses.OrderResponse;

import java.util.List;

public interface OrderService {
    public Order createOrder(OrderDTO orderDTO ) throws DataNotFoundException;
    public Order getOrderById(Long id) throws DataNotFoundException;
    public OrderResponse getAllOrders();
    public Order updateOrder(Long orderId, OrderDTO orderDTO) throws DataNotFoundException;
    public void deleteOrder(Long id);
    public List<Order> getOrdersByUserId(Long userId);
}
