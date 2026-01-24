package com.zimpy.cart.repositories;

import com.zimpy.cart.entity.Cart;
import com.zimpy.cart.entity.CartStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUserIdAndStatus(Long userId, CartStatus status);

}
