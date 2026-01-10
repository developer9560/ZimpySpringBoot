package com.zimpy.productImage.entity;

import com.zimpy.products.entity.Product;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "product_images",
        indexes = @Index(name = "idx_product_images_product_id",columnList = "product_id")
)
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id",nullable = false)
    private Product product;

    @Column(name = "image_url",nullable = false,length = 500)
    private String imageUrl;
    @Column(name = "is_primary",nullable = false)
    private boolean primaryImage = false;

    @Column(name = "sort_order",nullable = false)
    private int sortOrder;
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate(){
        createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    public boolean isPrimaryImage() {
        return primaryImage;
    }

    public void setPrimaryImage(boolean primaryImage) {
        this.primaryImage = primaryImage;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

}
