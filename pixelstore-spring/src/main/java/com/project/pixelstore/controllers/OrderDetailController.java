package com.project.pixelstore.controllers;

import com.project.pixelstore.dtos.OrderDetailDTO;
import com.project.pixelstore.mappers.OrderDetailMapper;
import com.project.pixelstore.models.OrderDetail;
import com.project.pixelstore.responses.ApiResponse;
import com.project.pixelstore.responses.OrderDetailResponse;
import com.project.pixelstore.services.OrderDetailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/order-details")
@RequiredArgsConstructor
public class OrderDetailController {
    private final OrderDetailService orderDetailService;
    private final OrderDetailMapper orderDetailMapper;

    // ADd new Order detail
    @PostMapping("")
    public ApiResponse<?> createOrderDetail(
            @Valid @RequestBody OrderDetailDTO orderDetailDTO,
            BindingResult bindingResult
    ) {
        try {
            if (bindingResult.hasErrors()) {
                List<String> errors = bindingResult.getFieldErrors().stream()
                        .map(FieldError::getDefaultMessage).toList();
                return ApiResponse.badRequest(errors);
            }
            OrderDetail orderDetail = orderDetailService.createOrderDetail(orderDetailDTO);
            return ApiResponse.ok(orderDetailMapper.toOrderDetailResponse(orderDetail));
        } catch (Exception e) {
            return ApiResponse.badRequest(List.of(e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ApiResponse<?> getOrderDetail(@Valid @PathVariable("id") Long id) {
        try {
            OrderDetail orderDetail = orderDetailService.getOrderDetailById(id);
            return ApiResponse.ok(orderDetailMapper.toOrderDetailResponse(orderDetail));
        } catch (Exception e) {
            return ApiResponse.badRequest(List.of(e.getMessage()));
        }
    }

    // get order details by order id
    @GetMapping("/order/{id}")
    public ApiResponse<?> getOrderDetailsByOrderId(@PathVariable("id") Long orderId) {
        try {
            List<OrderDetail> orderDetailList = orderDetailService.getOrderDetailsByOrderId(orderId);
            List<OrderDetailResponse> orderDetailResponses = orderDetailList.stream()
                    .map(orderDetailMapper::toOrderDetailResponse).toList();
            return ApiResponse.ok(orderDetailResponses);
        } catch (Exception e) {
            return ApiResponse.badRequest(List.of(e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ApiResponse<?> updateOrderDetail(
            @Valid @PathVariable Long id,
            @Valid @RequestBody OrderDetailDTO orderDetailDTO,
            BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                List<String> errors = bindingResult.getFieldErrors().stream()
                        .map(FieldError::getDefaultMessage).toList();
                return ApiResponse.badRequest(errors);
            }
            OrderDetail orderDetail = orderDetailService.updateOrderDetail(id, orderDetailDTO);
            return ApiResponse.ok(orderDetailMapper.toOrderDetailResponse(orderDetail));
        } catch (Exception e) {
            return ApiResponse.badRequest(List.of(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ApiResponse<?> deleteOrderDetail(@Valid @PathVariable Long id) {
        try {
            if (orderDetailService.getOrderDetailById(id) != null) {
                orderDetailService.deleteOrderDetail(id);
                return ApiResponse.ok(null);
            }
            return ApiResponse.badRequest(null);
        } catch (Exception e) {
            return ApiResponse.badRequest(List.of(e.getMessage()));
        }
    }
}
