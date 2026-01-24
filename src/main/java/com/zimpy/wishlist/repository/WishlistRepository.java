package com.zimpy.wishlist.repository;

import com.zimpy.wishlist.entity.Wishlist;
import com.zimpy.wishlist.entity.WishlistId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishlistRepository extends JpaRepository<Wishlist, WishlistId> {
        boolean existsByUserIdAndProductId(Long userId, Long productId);
        List<Wishlist> findByUserId(Long userId);
        void deleteByUserIdAndProductId(Long userId,Long productId);
}
