package com.zimpy.inventory.repository;

import com.zimpy.inventory.entity.Inventory;
import jakarta.persistence.LockModeType;
import org.hibernate.boot.models.JpaAnnotations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory , Long> {
    Optional<Inventory> findBySkuId(Long skuId);
    @Query("Select i from Inventory i where i.sku.id = :skuId")
    List<Inventory> findListSkuId(Long skuId);
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(" select i from Inventory i where i.sku.id = :skuId")
    Optional<Inventory> findBySkuIdForUpdate(Long skuId);

}
