package com.project.pixelstore.controllers;

import com.project.pixelstore.dtos.OrderDTO;
import com.project.pixelstore.mappers.OrderMapper;
import com.project.pixelstore.models.Order;
import com.project.pixelstore.responses.ApiResponse;
import com.project.pixelstore.services.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @PostMapping("")
    public ApiResponse<?> createOrder(@Valid @RequestBody OrderDTO orderDTO
            , BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                List<String> errors = bindingResult.getFieldErrors()
                        .stream().map(FieldError::getDefaultMessage).toList();
                return ApiResponse.builder()
                        .message("BAD REQUEST")
                        .status(HttpStatus.BAD_REQUEST.value())
                        .errors(errors)
                        .build();
            }
            Order order = orderService.createOrder(orderDTO);
            return ApiResponse.ok(orderMapper.toOrderResponse(order));
        } catch (Exception e) {
            return ApiResponse.builder()
                    .message("BAD REQUEST")
                    .status(HttpStatus.BAD_REQUEST.value())
                    .errors(List.of(e.getMessage()))
                    .build();
        }
    }

    @GetMapping("/user/{id}") // orders/user/{ user_id }
    public ApiResponse<?> getOrdersByUserId(@PathVariable("id") Long userId) {
        try {
            List<Order> orders = orderService.getOrdersByUserId(userId);
            return ApiResponse.ok(
                    orders.stream().map(
                            orderMapper::toOrderResponse
                    )
            );
        } catch (Exception e) {
            return ApiResponse.builder()
                    .message("BAD REQUEST")
                    .status(HttpStatus.BAD_REQUEST.value())
                    .errors(List.of(e.getMessage()))
                    .build();
        }
    }

    @GetMapping("/{orderId}") // orders/{ order_id }
    public ApiResponse<?> getOrderById(@PathVariable("orderId") Long orderId) {
        try {
            Order order = orderService.getOrderById(orderId);
            return ApiResponse.ok(orderMapper.toOrderResponse(order));
        } catch (Exception e) {
            return ApiResponse.builder()
                    .message("BAD REQUEST")
                    .status(HttpStatus.BAD_REQUEST.value())
                    .errors(List.of(e.getMessage()))
                    .build();
        }
    }

    @PutMapping("/{id}")
    public ApiResponse<?> updateOrder(@Valid @PathVariable Long id, @Valid @RequestBody OrderDTO orderDTO) {
        try {
            Order order = orderService.updateOrder(id, orderDTO);
            return ApiResponse.ok(orderMapper.toOrderResponse(order));
        } catch (Exception e) {
            return ApiResponse.builder()
                    .message("BAD REQUEST")
                    .status(HttpStatus.BAD_REQUEST.value())
                    .errors(List.of(e.getMessage()))
                    .build();
        }
    }

    @DeleteMapping("/{id}")
    public ApiResponse<?> deleteOrder(@Valid @PathVariable Long id) {
        try {
            orderService.deleteOrder(id);
            Order order = orderService.getOrderById(id);
            return ApiResponse.ok(orderMapper.toOrderResponse(order));
        } catch (Exception e) {
            return ApiResponse.builder()
                    .message("BAD REQUEST")
                    .status(HttpStatus.BAD_REQUEST.value())
                    .errors(List.of(e.getMessage()))
                    .build();
        }
    }
}
