package com.zimpy.cart.service;

import com.zimpy.cart.dto.AddToCartRequest;
import com.zimpy.cart.dto.CartItemResponse;
import com.zimpy.cart.dto.CartResponse;
import com.zimpy.cart.entity.Cart;
import com.zimpy.cart.entity.CartItem;
import com.zimpy.cart.entity.CartStatus;
import com.zimpy.cart.repositories.CartItemRepository;
import com.zimpy.cart.repositories.CartRepository;
import com.zimpy.exception.BadRequestException;
import com.zimpy.exception.ResourceNotFoundException;
import com.zimpy.inventory.Service.InventoryService;
import com.zimpy.productsku.repository.ProductSkuRepository;
import com.zimpy.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;
    private final InventoryService inventoryService;
    private final ProductSkuRepository productSkuRepository;

    public CartService(
            ProductSkuRepository productSkuRepository,
            InventoryService inventoryService,
            UserRepository userRepository,
            CartRepository cartRepository, CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
        this.inventoryService = inventoryService;
        this.productSkuRepository = productSkuRepository;
    }
    private Cart getOrCreateCart(Long userId){
        return cartRepository.findByUserIdAndStatus(userId, CartStatus.ACTIVE)
                .orElseGet(()->{
                    Cart cart= new Cart();
                    cart.setUser(userRepository.getReferenceById(userId));
                    return cartRepository.save(cart);
                });
    }

    @Transactional
    public void addItem(Long userId, AddToCartRequest request){

        Cart cart = getOrCreateCart(userId);

        CartItem item = cartItemRepository
                .findByCartIdAndSkuId(cart.getId(), request.getSkuId())
                .orElse(null);

        if(item==null){
            inventoryService.reserveStock(request.getSkuId(), request.getQuantity());

            item = new CartItem();
            item.setCart(cart);
            item.setSku(productSkuRepository.getReferenceById(request.getSkuId()));
            item.setQuantity(request.getQuantity());
            cartItemRepository.save(item);
        } else{
            int diff = request.getQuantity() - item.getQuantity();
            if(diff>0){
                inventoryService.reserveStock(request.getSkuId(), diff);
            }else if(diff<0){
                inventoryService.releaseStock(request.getSkuId(), -diff);
            }
            item.setQuantity(request.getQuantity());
        }
    }
    @Transactional
    public void removeItem(Long userId, Long skuId) {

        Cart cart = getOrCreateCart(userId);

        CartItem item = cartItemRepository
                .findByCartIdAndSkuId(cart.getId(), skuId)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found"));

        inventoryService.releaseStock(skuId, item.getQuantity());

        cartItemRepository.delete(item);
    }

    public CartResponse getMyCart(Long userId) {

        Cart cart = cartRepository
                .findByUserIdAndStatus(userId, CartStatus.ACTIVE)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Active cart not found")
                );

        List<CartItem> items = cartItemRepository.findByCartId(cart.getId());

        BigDecimal totalAmount = BigDecimal.ZERO;
        List<CartItemResponse> itemResponses = new ArrayList<>();

        for (CartItem item : items) {
            BigDecimal price = item.getSku().getPrice();
            BigDecimal itemTotal =
                    price.multiply(BigDecimal.valueOf(item.getQuantity()));

            totalAmount = totalAmount.add(itemTotal);

            itemResponses.add(
                    new CartItemResponse(
                            item.getSku().getId(),
                            item.getSku().getSkuCode(),
                            item.getQuantity(),
                            price
                    )
            );
        }

        return new CartResponse(
                cart.getId(),
                itemResponses,
                totalAmount
        );
    }


    @Transactional
    public void updateItem(Long userId, Long skuId, int newQuantity) {

        if (newQuantity <= 0) {
            throw new BadRequestException("Quantity must be greater than zero");
        }

        Cart cart = getOrCreateCart(userId);

        CartItem item = cartItemRepository
                .findByCartIdAndSkuId(cart.getId(), skuId)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found in cart"));

        int currentQuantity = item.getQuantity();
        int diff = newQuantity - currentQuantity;

        if (diff > 0) {
            // user increased quantity → reserve more
            inventoryService.reserveStock(skuId, diff);
        } else if (diff < 0) {
            // user decreased quantity → release stock
            inventoryService.releaseStock(skuId, -diff);
        }

        item.setQuantity(newQuantity);
        cartItemRepository.save(item);
    }


    // usefull when user logs out, wants freash cart
//    @Transactional
//    public void clearCart(Long userId) {
//        Cart cart = getActiveCart(userId);
//        List<CartItem> items = cartItemRepository.findByCartId(cart.getId());
//
//        for (CartItem item : items) {
//            inventoryService.releaseStock(
//                    item.getSku().getId(),
//                    item.getQuantity()
//            );
//        }
//        cartItemRepository.deleteAll(items);
//    }
//
//    private Cart getActiveCart(Long userId) {
//        Cart cart = cartRepository.findByUserIdAndStatus(userId,CartStatus.ACTIVE).orElseThrow(()-> new ResourceNotFoundException("not found active cart"));
//        return  cart;
//    }


}
