package com.zimpy.catalog.repository;

import com.zimpy.catalog.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByParentIsNullAndDeletedAtIsNull();

    Optional<Category> findByIdAndDeletedAtIsNull(Long id);
    Optional<Category> findBySlugAndDeletedAtIsNull(String slug);

    boolean existsBySlugAndDeletedAtIsNull(String slug);
    boolean existsByParentAndDeletedAtIsNull(Category parent);
    List<Category> findByParent(Category parent);

}

