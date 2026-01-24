package com.zimpy.order.controller;

import com.zimpy.common.ApiResponse;
import com.zimpy.order.dto.AdminOrderDetailResponse;
import com.zimpy.order.dto.AdminOrderListResponse;
import com.zimpy.order.dto.UpdateOrderStatusRequest;
import com.zimpy.order.entity.OrderStatus;
import com.zimpy.order.service.AdminOrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/orders")
@PreAuthorize("hasRole('ADMIN')")

public class AdminOrderController {

    private final AdminOrderService service;

    public AdminOrderController(AdminOrderService service) {
        this.service = service;
    }

    // Get all orders (filters + pagination)
    @GetMapping
    public ResponseEntity<ApiResponse<Page<AdminOrderListResponse>>> getAll(
            @RequestParam(required = false) OrderStatus status,
            @RequestParam(required = false) Long userId,
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                new ApiResponse<>(
                        200,
                        "Orders fetched",
                        service.getAllOrders(status, userId, pageable)
                )
        );
    }

    // Get order by id
    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse<AdminOrderDetailResponse>> getOne(
            @PathVariable Long orderId
    ) {
        return ResponseEntity.ok(
                new ApiResponse<>(
                        200,
                        "Order fetched",
                        service.getOrder(orderId)
                )
        );
    }

    // Update order status
    @PatchMapping("/{orderId}/status")
    public ResponseEntity<ApiResponse<Void>> updateStatus(
            @PathVariable Long orderId,
            @RequestBody UpdateOrderStatusRequest request
    ) {
        service.updateStatus(orderId, request.getStatus());
        return ResponseEntity.ok(
                new ApiResponse<>(200, "Order status updated", null)
        );
    }

    //Cancel order (admin override)
    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<ApiResponse<Void>> cancel(
            @PathVariable Long orderId
    ) {
        service.cancelOrder(orderId);
        return ResponseEntity.ok(
                new ApiResponse<>(200, "Order cancelled by admin", null)
        );
    }
}
