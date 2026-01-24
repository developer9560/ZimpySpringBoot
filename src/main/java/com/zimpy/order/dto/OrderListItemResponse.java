package com.zimpy.order.dto;


import com.zimpy.order.entity.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderListItemResponse {
    private Long orderId;
    private OrderStatus status;
    private BigDecimal totalAmount;
    private LocalDateTime createdAt;

    public OrderListItemResponse(Long orderId, OrderStatus status,
                                 BigDecimal totalAmount, LocalDateTime createdAt) {
        this.orderId = orderId;
        this.status = status;
        this.totalAmount = totalAmount;
        this.createdAt = createdAt;
    }
    // getters

    public Long getOrderId() {
        return orderId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}

