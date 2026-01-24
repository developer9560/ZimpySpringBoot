package com.zimpy.productsku.repository;

import com.zimpy.products.entity.Product;
import com.zimpy.productsku.entity.ProductSku;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface ProductSkuRepository extends JpaRepository<ProductSku , Long> {
    List<ProductSku> findByProduct_IdAndDeletedAtIsNullOrderByCreatedAtDesc(Long productId);

    Optional<ProductSku> findByIdAndDeletedAtIsNull(Long id);

    boolean existsBySkuCodeAndDeletedAtIsNull(String skuCode);
}
