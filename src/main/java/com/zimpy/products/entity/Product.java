package com.zimpy.products.entity;

import com.zimpy.catalog.entity.Category;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "products",
        uniqueConstraints = {@UniqueConstraint(name = "uk_products_slug", columnNames = "slug")
    }
)

public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false , unique = true)
    private String slug;
    @Column(columnDefinition = "TEXT" , nullable = false)
    private String summary;
    @Column(columnDefinition = "TEXT")
    private String description ;
    private String brand;
    @Column(nullable = false)
    private boolean is_active ;
    @Column(name = "created_at",nullable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at",nullable = false)
    private LocalDateTime updatedAt;
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "category_id",nullable = false)
    private Category category;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }


}
