package com.zimpy.products.dto;

import jakarta.validation.constraints.NotBlank;

public class ProductUpdateRequest {
    private String name;
    private String summary;
    private String description;
    private String brand;
    private Long categoryId;
    private Boolean isActive;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Boolean getActive() {
        return isActive;

    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
