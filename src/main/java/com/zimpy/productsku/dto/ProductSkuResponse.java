package com.zimpy.productsku.dto;

import java.math.BigDecimal;

public class ProductSkuResponse {
    private Long id;
    private String skuCode;
    private BigDecimal price;
    private BigDecimal mrp;
    private Long productId;

    public ProductSkuResponse(Long id, String skuCode, BigDecimal price, BigDecimal mrp ,Long productId) {
        this.id = id;
        this.skuCode = skuCode;
        this.price = price;
        this.mrp = mrp;
        this.productId = productId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public BigDecimal getMrp() {
        return mrp;
    }

    public void setMrp(BigDecimal mrp) {
        this.mrp = mrp;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
