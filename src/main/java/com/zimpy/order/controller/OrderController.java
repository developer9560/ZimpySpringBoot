package com.zimpy.order.controller;

import com.zimpy.common.ApiResponse;
import com.zimpy.order.dto.OrderResponse;
import com.zimpy.order.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@PreAuthorize("hasRole('USER')")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service)
    {
        this.service = service;
    }

    @PostMapping("/checkout")
    public ResponseEntity<ApiResponse<OrderResponse>> checkout(
            Authentication auth
    ) {
        Long userId = (Long) auth.getPrincipal();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        200,
                        "Checkout successful",
                        service.checkout(userId)
                )
        );
    }
}

