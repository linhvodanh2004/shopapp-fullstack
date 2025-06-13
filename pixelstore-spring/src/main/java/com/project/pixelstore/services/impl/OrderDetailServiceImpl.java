package com.project.pixelstore.services.impl;

import com.project.pixelstore.dtos.OrderDetailDTO;
import com.project.pixelstore.exceptions.DataNotFoundException;
import com.project.pixelstore.mappers.OrderDetailMapper;
import com.project.pixelstore.models.Order;
import com.project.pixelstore.models.OrderDetail;
import com.project.pixelstore.models.Product;
import com.project.pixelstore.repositories.OrderDetailRepository;
import com.project.pixelstore.repositories.OrderRepository;
import com.project.pixelstore.repositories.ProductRepository;
import com.project.pixelstore.services.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderDetailServiceImpl implements OrderDetailService {
    private final OrderDetailRepository orderDetailRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderDetailMapper orderDetailMapper;
    @Override
    public OrderDetail createOrderDetail(OrderDetailDTO orderDetailDTO) throws DataNotFoundException {
        Order order = orderRepository.findById(orderDetailDTO.getOrderId())
                .orElseThrow(() -> new DataNotFoundException("Order not found with id: "
                        + orderDetailDTO.getOrderId()));
        Product product = productRepository.findById(orderDetailDTO.getProductId())
                .orElseThrow(() -> new DataNotFoundException("Product not found with id: "
                        + orderDetailDTO.getProductId()));
        OrderDetail orderDetail = orderDetailMapper.toOrderDetail(orderDetailDTO);
        orderDetail.setOrder(order);
        orderDetail.setProduct(product);
        return orderDetailRepository.save(orderDetail);
    }

    @Override
    public OrderDetail getOrderDetailById(Long id) throws DataNotFoundException {
        return orderDetailRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Order detail not found with id: " + id));
    }

    @Override
    @Transactional
    public void deleteOrderDetail(Long id) {
        orderDetailRepository.deleteById(id);
    }

    @Override
    @Transactional
    public OrderDetail updateOrderDetail(Long id, OrderDetailDTO orderDetailDTO) throws DataNotFoundException {
        OrderDetail orderDetail = orderDetailRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Order detail not found with id: "
                        + id));
        Order order = orderRepository.findById(orderDetailDTO.getOrderId())
                .orElseThrow(() -> new DataNotFoundException("Order not found with id: "
                        + orderDetailDTO.getOrderId()));
        Product product = productRepository.findById(orderDetailDTO.getProductId())
                .orElseThrow(() -> new DataNotFoundException("Product not found with id: "
                        + orderDetailDTO.getProductId()));
        orderDetailMapper.updateOrderDetail(orderDetail, orderDetailDTO);
        orderDetail.setOrder(order);
        orderDetail.setProduct(product);
        return orderDetailRepository.save(orderDetail);
    }

    @Override
    public List<OrderDetail> getOrderDetailsByOrderId(Long orderId) {
        return orderDetailRepository.findByOrderId(orderId);
    }
}
