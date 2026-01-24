package com.zimpy.wishlist.dto;

public class WishlistProductResponse {
    private Long id;
    private String name;
    private String slug;
    private String summary;

    public WishlistProductResponse(Long id, String name, String slug, String summary) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.summary = summary;
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
}
