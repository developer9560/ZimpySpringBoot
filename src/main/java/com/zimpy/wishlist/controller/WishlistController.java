package com.zimpy.wishlist.controller;

import com.zimpy.common.ApiResponse;
import com.zimpy.exception.BadRequestException;
import com.zimpy.exception.UnauthorizedException;
import com.zimpy.wishlist.dto.WishlistProductResponse;
import com.zimpy.wishlist.service.WishlistService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishlist")
@PreAuthorize("hasRole('USER')")
public class WishlistController {

    private final WishlistService service;

    public WishlistController(WishlistService service) {
        this.service = service;
    }

    @PostMapping("/{productId}")
    public ResponseEntity<ApiResponse<Void>> add(
            Authentication auth,
            @PathVariable Long productId
    ) {
        service.add((Long) auth.getPrincipal(), productId);
        return ResponseEntity.ok(
                new ApiResponse<>(200, "Added to wishlist", null)
        );
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse<Void>> remove(
            Authentication auth,
            @PathVariable Long productId
    ) {
        service.remove((Long) auth.getPrincipal(), productId);
        return ResponseEntity.ok(
                new ApiResponse<>(200, "Removed from wishlist", null)
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<WishlistProductResponse>>> getMy(Authentication authentication) {

        if(authentication==null || !authentication.isAuthenticated()){
            throw  new UnauthorizedException("please login");
        }
        Long userId  = (Long)  authentication.getPrincipal();


        return ResponseEntity.ok(new ApiResponse<>(
                        200,
                        "Wishlist fetched",
                        service.getMyWishlist(userId)
                )
        );
    }

    @GetMapping("/check/{productId}")
    public ResponseEntity<ApiResponse<Boolean>> check(
            Authentication auth,
            @PathVariable Long productId
    ) {
        return ResponseEntity.ok(
                new ApiResponse<>(
                        200,
                        "Status",
                        service.isWishlisted(
                                (Long) auth.getPrincipal(),
                                productId
                        )
                )
        );
    }
}

