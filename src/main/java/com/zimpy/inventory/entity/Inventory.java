package com.zimpy.inventory.entity;

import com.zimpy.productsku.entity.ProductSku;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "inventories",
        uniqueConstraints = {
        @UniqueConstraint(name = "uk_inventory_sku",columnNames = "sku_id")
        }

)
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sku_id",nullable = false)
    private ProductSku sku;
    @Column(name = "total_stock",nullable = false)
    private int totalStock;

    @Column(name = "reserved_stock",nullable = false)
    private int reservedStock;
    @Column(name = "available_stock",nullable = false)
    private int availableStock;

    @Column(name = "created_at",nullable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at",nullable = false)
    private LocalDateTime updateAt;

    @PrePersist
    void onCreate(){
        createdAt = LocalDateTime.now();
        updateAt = LocalDateTime.now();
    }

    @PreUpdate
    void onUpdate(){
        updateAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductSku getSku() {
        return sku;
    }

    public void setSku(ProductSku sku) {
        this.sku = sku;
    }

    public int getTotalStock() {
        return totalStock;
    }

    public void setTotalStock(int totalStock) {
        this.totalStock = totalStock;
    }

    public int getReservedStock() {
        return reservedStock;
    }

    public void setReservedStock(int reservedStock) {
        this.reservedStock = reservedStock;
    }

    public int getAvailableStock() {
        return availableStock;
    }

    public void setAvailableStock(int availableStock) {
        this.availableStock = availableStock;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }
}
