package com.zimpy.cart.dto;

import java.math.BigDecimal;

public class CartItemResponse {
    private Long skuId;
    private String skuCode;
    private int quantity;
    private BigDecimal price;

    public CartItemResponse(Long skuId, String skuCode, int quantity, BigDecimal price) {
        this.skuId = skuId;
        this.skuCode = skuCode;
        this.quantity = quantity;
        this.price = price;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
