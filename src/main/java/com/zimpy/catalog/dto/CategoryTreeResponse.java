package com.zimpy.catalog.dto;

import java.util.List;

public class CategoryTreeResponse {
    private  Long id;
    private  String name;
    private String slug;
    private int level;
    private List<CategoryTreeResponse> children;

    public CategoryTreeResponse(Long id, String name, String slug, int level, List<CategoryTreeResponse> children) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.level = level;
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

    public List<CategoryTreeResponse> getChildren() {
        return children;
    }
}
