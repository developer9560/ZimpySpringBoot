package com.zimpy.banner.repository;

import com.zimpy.banner.entity.Banner;
import com.zimpy.banner.entity.BannerPlacement;
import com.zimpy.banner.entity.BannerStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Long> {

        // ============ ADMIN QUERIES ============

        // Get all banners (including soft-deleted) for admin management
        @Query("SELECT b FROM Banner b WHERE b.deletedAt IS NULL ORDER BY b.priority ASC, b.createdAt DESC")
        Page<Banner> findAllActive(Pageable pageable);

        // Get banners by status
        Page<Banner> findByStatusAndDeletedAtIsNull(BannerStatus status, Pageable pageable);

        // Get banners by placement
        Page<Banner> findByPlacementAndDeletedAtIsNull(BannerPlacement placement, Pageable pageable);

        // Get banner by ID (admin can see all, even soft-deleted)
        Optional<Banner> findById(Long id);

        // ============ PUBLIC QUERIES ============

        /**
         * Get active banners for public display
         * Filters by:
         * - ACTIVE status
         * - Current date within start/end date range (or null dates)
         * - Current day of week (if showDays is set)
         * - Not soft-deleted
         * - Ordered by priority ASC
         */
        @Query("""
                            SELECT b FROM Banner b
                            WHERE b.status = 'ACTIVE'
                            AND b.deletedAt IS NULL
                            AND (b.startDate IS NULL OR b.startDate <= :now)
                            AND (b.endDate IS NULL OR b.endDate >= :now)
                            AND (:placement IS NULL OR b.placement = :placement)
                            ORDER BY b.priority ASC
                        """)
        List<Banner> findActiveBannersForPublic(
                        @Param("now") LocalDateTime now,
                        @Param("placement") BannerPlacement placement);

        /**
         * Get active banners filtered by specific day
         * Used for day-based scheduling
         */
        @Query("""
                            SELECT b FROM Banner b
                            WHERE b.status = 'ACTIVE'
                            AND b.deletedAt IS NULL
                            AND (b.startDate IS NULL OR b.startDate <= :now)
                            AND (b.endDate IS NULL OR b.endDate >= :now)
                            AND (:placement IS NULL OR b.placement = :placement)
                            AND (b.showDays IS NULL OR b.showDays = '' OR b.showDays LIKE CONCAT('%', :dayOfWeek, '%'))
                            ORDER BY b.priority ASC
                        """)
        List<Banner> findActiveBannersWithDayFilter(
                        @Param("now") LocalDateTime now,
                        @Param("placement") BannerPlacement placement,
                        @Param("dayOfWeek") String dayOfWeek);

        // ============ ANALYTICS ============

        // Increment click count
        @Modifying
        @Query("UPDATE Banner b SET b.totalClicked = b.totalClicked + 1 WHERE b.id = :id")
        void incrementClickCount(@Param("id") Long id);

        // Get top performing banners
        @Query("SELECT b FROM Banner b WHERE b.deletedAt IS NULL ORDER BY b.totalClicked DESC")
        List<Banner> findTopPerformingBanners(Pageable pageable);

        // ============ UTILITY QUERIES ============

        // Count active banners by placement
        @Query("SELECT COUNT(b) FROM Banner b WHERE b.placement = :placement AND b.status = 'ACTIVE' AND b.deletedAt IS NULL")
        long countActiveByPlacement(@Param("placement") BannerPlacement placement);

        // Check if priority already exists
        boolean existsByPriorityAndDeletedAtIsNull(int priority);

        // Soft delete
        @Modifying
        @Query("UPDATE Banner b SET b.deletedAt = :now WHERE b.id = :id")
        void softDelete(@Param("id") Long id, @Param("now") LocalDateTime now);
}
