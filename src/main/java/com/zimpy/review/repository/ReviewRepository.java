package com.zimpy.review.repository;

import com.zimpy.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
public interface ReviewRepository extends JpaRepository<Review, Long> {

    Optional<Review> findByUserIdAndProductIdAndDeletedAtIsNull(
            Long userId, Long productId
    );

    Page<Review> findByProductIdAndDeletedAtIsNull(
            Long productId, Pageable pageable
    );

    long countByProductIdAndDeletedAtIsNull(Long productId);

    @Query("""
        SELECT AVG(r.rating)
        FROM Review r
        WHERE r.product.id = :productId
          AND r.deletedAt IS NULL
    """)
    Double getAverageRating(Long productId);
}
