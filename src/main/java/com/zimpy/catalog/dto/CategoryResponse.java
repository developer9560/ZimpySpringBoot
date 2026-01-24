package com.zimpy.catalog.dto;



public class CategoryResponse {
    private Long id;
    private String name;
    private String slug;
    private String imageUrl;
    private int level;

    public CategoryResponse(Long id, String name, String slug, int level, String imageUrl) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.level = level;
        this.imageUrl = imageUrl;
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
