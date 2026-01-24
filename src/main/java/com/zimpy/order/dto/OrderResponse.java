package com.zimpy.order.dto;

import java.math.BigDecimal;

public class OrderResponse {
    private Long orderId;
    private BigDecimal totalAmoun;

    public OrderResponse(Long orderId, BigDecimal totalAmoun) {
        this.orderId = orderId;
        this.totalAmoun = totalAmoun;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getTotalAmoun() {
        return totalAmoun;
    }

    public void setTotalAmoun(BigDecimal totalAmoun) {
        this.totalAmoun = totalAmoun;
    }
}
