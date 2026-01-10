package com.zimpy.products.dto;

public class ProductResponse {
    private Long id;
    private String name;
    private String slug;
    private String summary;
    private String description;
    private String brand;
    private boolean isActive;

    private CategoryMiniResponse category;


    public ProductResponse(Long id, String name, String slug, String summary, String desctription, String brand, boolean isActive, CategoryMiniResponse category) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.summary = summary;
        this.description = desctription;
        this.brand = brand;
        this.isActive = isActive;
        this.category = category;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
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
        this.description= description;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }


    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public CategoryMiniResponse getCategory() {
        return category;
    }

    public void setCategory(CategoryMiniResponse category) {
        this.category = category;
    }
}
