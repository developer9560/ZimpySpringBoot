package com.zimpy.order.dto;

import java.math.BigDecimal;

public class OrderItemResponse {
    private Long skuId;
    private String skuCode;
    private BigDecimal price;
    private int quantity;

    public OrderItemResponse(Long skuId, String skuCode,
                             BigDecimal price, int quantity) {
        this.skuId = skuId;
        this.skuCode = skuCode;
        this.price = price;
        this.quantity = quantity;
    }
    // getters

    public Long getSkuId() {
        return skuId;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}

