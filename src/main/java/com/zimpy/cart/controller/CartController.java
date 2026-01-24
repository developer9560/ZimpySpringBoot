package com.zimpy.cart.controller;

import com.zimpy.cart.dto.AddToCartRequest;
import com.zimpy.cart.dto.CartResponse;
import com.zimpy.cart.dto.UpdateCartItemRequest;
import com.zimpy.cart.service.CartService;
import com.zimpy.common.ApiResponse;
import com.zimpy.exception.UnauthorizedException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@PreAuthorize("hasRole('USER')")
public class CartController {

    private final CartService service;

    public CartController(CartService service) {
        this.service = service;
    }

    @PostMapping("/items")
    public ResponseEntity<ApiResponse<Void>> addItem(
            Authentication auth,
            @RequestBody AddToCartRequest request
    ) {


        if(auth == null || !auth.isAuthenticated()){
            throw  new UnauthorizedException(" please login");
        }
        Long useId = (Long) auth.getPrincipal();

        service.addItem(useId, request);
        return ResponseEntity.ok(new ApiResponse<>(200, "Item added", null));
    }

    @DeleteMapping("/items/{skuId}")
    public ResponseEntity<ApiResponse<Void>> removeItem(
            Authentication auth,
            @PathVariable Long skuId
    ) {
        service.removeItem((Long) auth.getPrincipal(), skuId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Item removed", null));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<CartResponse>> getCart(
            Authentication auth
    ) {
        return ResponseEntity.ok(
                new ApiResponse<>(200, "Cart fetched",
                        service.getMyCart((Long) auth.getPrincipal()))
        );
    }
    @PutMapping("/items/{skuId}")
    public ResponseEntity<ApiResponse<Void>> updateItem(
            Authentication authentication,
            @PathVariable Long skuId,
            @RequestBody UpdateCartItemRequest request
    ) {
        Long userId = (Long) authentication.getPrincipal();
        service.updateItem(userId, skuId, request.getQuantity());

        return ResponseEntity.ok(
                new ApiResponse<>(200, "Cart item updated", null)
        );
    }

//    @DeleteMapping// user logs out , want fresh cart ,
//    public ResponseEntity<ApiResponse<Void>> clearCart()
}
