package com.project.shopapp.services;

import com.project.shopapp.dtos.OrderDTO;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.models.Order;
import com.project.shopapp.responses.OrderResponse;

import java.util.List;

public interface IOrderService {
    public Order createOrder(OrderDTO orderDTO ) throws DataNotFoundException;
    public Order getOrderById(Long id) throws DataNotFoundException;
    public OrderResponse getAllOrders();
    public Order updateOrder(Long orderId, OrderDTO orderDTO) throws DataNotFoundException;
    public void deleteOrder(Long id);
    public List<Order> getOrdersByUserId(Long userId);
}
