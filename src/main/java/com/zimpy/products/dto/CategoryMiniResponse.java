package com.zimpy.products.dto;


public class CategoryMiniResponse {
    private Long id;
    private String name;
    private String slug;

    public CategoryMiniResponse(Long id, String name, String slug) {
        this.id = id;
        this.name = name;
        this.slug = slug;
    }

}
