package com.zimpy.productImage.dto;

public class ProductImageResponse {
    private Long id;
    private String imageUrl;
    private boolean isPrimary;
    private int sortOrder;
    private  Long productId;

    public ProductImageResponse(Long id, boolean isPrimary, int sortOrder, String imageUrl, Long productId) {
        this.id = id;
        this.isPrimary = isPrimary;
        this.sortOrder = sortOrder;
        this.imageUrl = imageUrl;
        this.productId = productId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    public void setPrimary(boolean primary) {
        isPrimary = primary;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
