package com.zimpy.catalog.dto;

import java.util.List;

public class CategoryTreeResponse {
    private Long id;
    private String name;
    private String slug;
    private int level;
    private String imageUrl;
    private List<CategoryTreeResponse> children;

    public CategoryTreeResponse(Long id, String name, String slug, int level, String imageUrl,
            List<CategoryTreeResponse> children) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.level = level;
        this.imageUrl = imageUrl;
        this.children = children;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSlug() {
        return slug;
    }

    public int getLevel() {
        return level;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public List<CategoryTreeResponse> getChildren() {
        return children;
    }
}
