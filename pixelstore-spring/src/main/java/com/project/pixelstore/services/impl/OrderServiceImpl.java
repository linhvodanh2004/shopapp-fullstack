package com.project.pixelstore.services.impl;

import com.project.pixelstore.dtos.OrderDTO;
import com.project.pixelstore.exceptions.DataNotFoundException;
import com.project.pixelstore.mappers.OrderMapper;
import com.project.pixelstore.models.Order;
import com.project.pixelstore.constant.OrderStatus;
import com.project.pixelstore.models.User;
import com.project.pixelstore.repositories.OrderRepository;
import com.project.pixelstore.repositories.UserRepository;
import com.project.pixelstore.responses.OrderResponse;
import com.project.pixelstore.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderMapper orderMapper;

    @Override
    public Order createOrder(OrderDTO orderDTO) throws DataNotFoundException {
        User user = userRepository.findById(orderDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException("User not found with id: " + orderDTO.getUserId()));
        Order order = orderMapper.toOrder(orderDTO);
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);
        LocalDateTime shippingDate = orderDTO.getShippingDate() == null ? LocalDateTime.now() : orderDTO.getShippingDate();
        if (shippingDate.isBefore(LocalDateTime.now())) {
            throw new DataNotFoundException("Shipping date is invalid");
        }
        order.setShippingDate(shippingDate);
        order.setActive(true);
        orderRepository.save(order);
        return orderRepository.save(order);
    }

    @Override
    public Order getOrderById(Long id) throws DataNotFoundException {
        return orderRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Order not found with id: " + id));
    }

    @Override
    public OrderResponse getAllOrders() {
        return null;
    }

    @Override
    @Transactional
    public Order updateOrder(Long orderId, OrderDTO orderDTO) throws DataNotFoundException {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new DataNotFoundException("Order not found with id: " + orderId));
        User user = userRepository.findById(orderDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException("User not found with id: " + orderDTO.getUserId()));

        orderMapper.updateOrder(order, orderDTO);
        LocalDateTime shippingDate = orderDTO.getShippingDate() == null ? LocalDateTime.now() : orderDTO.getShippingDate();
//        if (shippingDate.isBefore(LocalDateTime.now())) {
//            throw new DataNotFoundException("Shipping date is invalid");
//        }
        order.setShippingDate(shippingDate);
        order.setUser(user);
        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public void deleteOrder(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            order.get().setActive(false);
            orderRepository.save(order.get());
        }
    }

    @Override
    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }
}
