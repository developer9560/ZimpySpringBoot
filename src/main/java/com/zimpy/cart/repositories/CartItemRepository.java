package com.zimpy.cart.repositories;

import com.zimpy.cart.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    Optional<CartItem> findByCartIdAndSkuId(Long cartId, Long skuId);
    List<CartItem> findByCartId(Long cartId);
}
