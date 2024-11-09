package com.yuva.order.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yuva.order.dto.OrderRequest;
import com.yuva.order.dto.OrderResponse;
import com.yuva.order.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("")
    public ResponseEntity<?> createOrder(@RequestParam String productId, @RequestParam Integer quantity, HttpServletRequest request) throws JsonProcessingException {
        String username = request.getHeader("Authenticated-Username");
        System.out.println(username);
        return orderService.createOrder(productId, quantity, username);
    }

    @PostMapping("/{orderId}/{status}")
    public ResponseEntity<?> updateOrderStatus(@PathVariable Long orderId, @PathVariable String status, HttpServletRequest request ) {
        if (!"ADMIN".equals(request.getHeader("Authenticated-Role"))) {
           return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to perform this action");
        }
        return orderService.updateOrderStatus(orderId, status);
    }
}
