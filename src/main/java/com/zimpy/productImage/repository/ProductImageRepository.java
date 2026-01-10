package com.zimpy.productImage.repository;

import com.fasterxml.jackson.annotation.OptBoolean;
import com.zimpy.productImage.entity.ProductImage;
import com.zimpy.products.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductImageRepository extends JpaRepository<ProductImage,Long> {
    List<ProductImage> findByProductOrderBySortOrderAsc(Product product);
    Optional<ProductImage> findByProductAndPrimaryImageTrue(Product product);


}
