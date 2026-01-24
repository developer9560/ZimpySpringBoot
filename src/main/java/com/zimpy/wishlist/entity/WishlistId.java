package com.zimpy.wishlist.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class WishlistId implements Serializable {

    private Long userId;
    private Long productId;

    public WishlistId() {}

    public WishlistId(Long userId, Long productId) {
        this.userId = userId;
        this.productId = productId;
    }

    // equals + hashCode (MANDATORY)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WishlistId)) return false;
        WishlistId that = (WishlistId) o;
        return Objects.equals(userId, that.userId)
                && Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, productId);
    }
}
