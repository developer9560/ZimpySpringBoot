package com.zimpy.order.dto;

import com.zimpy.order.entity.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class AdminOrderDetailResponse {
    private Long id;
    private Long userId;
    private OrderStatus orderStatus;
    private BigDecimal totalAmount;
    private LocalDateTime createAt;
    private List<OrderItemResponse> item;

    public AdminOrderDetailResponse(Long id, Long userId, OrderStatus orderStatus, BigDecimal totalAmount, LocalDateTime createAt, List<OrderItemResponse> item) {
        this.id = id;
        this.userId = userId;
        this.orderStatus = orderStatus;
        this.totalAmount = totalAmount;
        this.createAt = createAt;
        this.item = item;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public List<OrderItemResponse> getItem() {
        return item;
    }

    public void setItem(List<OrderItemResponse> item) {
        this.item = item;
    }
}
