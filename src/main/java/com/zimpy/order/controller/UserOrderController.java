package com.zimpy.order.controller;

import com.zimpy.order.dto.OrderDetailResponse;
import com.zimpy.order.dto.OrderListItemResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.zimpy.common.ApiResponse;

import com.zimpy.order.service.OrderService;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@PreAuthorize("hasRole('USER')")
public class UserOrderController {

    private final OrderService orderService;

    public UserOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<OrderListItemResponse>>> myOrders(
            Authentication auth,
            Pageable pageable
    ) {
        Long userId = (Long) auth.getPrincipal();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        200,
                        "Orders fetched",
                        orderService.getMyOrders(userId, pageable)
                )
        );
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse<OrderDetailResponse>> myOrder(
            Authentication auth,
            @PathVariable Long orderId
    ) {
        Long userId = (Long) auth.getPrincipal();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        200,
                        "Order fetched",
                        orderService.getMyOrder(userId, orderId)
                )
        );
    }

    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<ApiResponse<Void>> cancel(
            Authentication auth,
            @PathVariable Long orderId
    ) {
        Long userId = (Long) auth.getPrincipal();
        orderService.cancelMyOrder(userId, orderId);

        return ResponseEntity.ok(
                new ApiResponse<>(200, "Order cancelled", null)
        );
    }
}

