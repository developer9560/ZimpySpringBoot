package com.zimpy.products.repository;

import com.zimpy.catalog.entity.Category;
import com.zimpy.products.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product ,Long>
{


    boolean existsBySlugAndDeletedAtIsNull(String slug);

    List<Product> findTop8ByCategoryAndDeletedAtIsNullAndIdNot(
            Category category,
            Long id
    );


    Optional<Product> findByIdAndDeletedAtIsNull(Long id);

        Optional<Product> findBySlugAndDeletedAtIsNull(String slug);

        Page<Product> findByDeletedAtIsNull(Pageable pageable);

        Page<Product> findByCategory_SlugAndDeletedAtIsNull(
                String slug,
                Pageable pageable
        );

        Page<Product> findByNameContainingIgnoreCaseAndDeletedAtIsNull(
                String name,
                Pageable pageable
        );


        Page<Product> findByBrandIgnoreCaseAndDeletedAtIsNull(
                String brand,
                Pageable pageable
        );

        List<Product> findTop10ByDeletedAtIsNullOrderByCreatedAtDesc();

        // placeholders for later
        List<Product> findTop10ByDeletedAtIsNullOrderByIdDesc();




}
