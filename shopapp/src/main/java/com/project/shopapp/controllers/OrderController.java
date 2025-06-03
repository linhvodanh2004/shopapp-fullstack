package com.project.shopapp.controllers;

import com.project.shopapp.dtos.OrderDTO;
import com.project.shopapp.models.Order;
import com.project.shopapp.responses.OrderResponse;
import com.project.shopapp.services.IOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/orders")
@RequiredArgsConstructor
public class OrderController {
    private final IOrderService orderService;

    @PostMapping("")
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderDTO orderDTO
            , BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                List<String> errors = bindingResult.getFieldErrors()
                        .stream().map(FieldError::getDefaultMessage).toList();
                return ResponseEntity.badRequest().body(errors);
            }
            Order order = orderService.createOrder(orderDTO);
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user/{id}") // orders/user/{ user_id }
    public ResponseEntity<?> getOrdersByUserId(@PathVariable("id") Long userId) {
        try {
            List<Order> orderResponses = orderService.getOrdersByUserId(userId);
            return ResponseEntity.ok(orderResponses);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/{orderId}") // orders/{ order_id }
    public ResponseEntity<?> getOrderById(@PathVariable("orderId") Long orderId) {
        try {
            Order order = orderService.getOrderById(orderId);
            return ResponseEntity.ok(order);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(@Valid @PathVariable Long id, @Valid @RequestBody OrderDTO orderDTO) {
        try {
            Order orderResponse = orderService.updateOrder(id, orderDTO);
            return ResponseEntity.ok(orderResponse);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@Valid @PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok("Test delete order " + id);
    }
}
