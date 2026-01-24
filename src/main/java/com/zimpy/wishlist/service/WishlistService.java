package com.zimpy.wishlist.service;

import com.zimpy.products.repository.ProductRepository;
import com.zimpy.user.repository.UserRepository;
import com.zimpy.wishlist.dto.WishlistProductResponse;
import com.zimpy.wishlist.entity.Wishlist;
import com.zimpy.wishlist.entity.WishlistId;
import com.zimpy.wishlist.repository.WishlistRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistService {
    private final WishlistRepository repo;
    private final ProductRepository productRepo;
    private final UserRepository userRepo;

    public WishlistService(WishlistRepository repo, ProductRepository productRepo, UserRepository userRepo) {
        this.repo = repo;
        this.productRepo = productRepo;
        this.userRepo = userRepo;
    }

    public void add(Long userId, Long productId){
        if(repo.existsByUserIdAndProductId(userId,productId)){
            return;//idempotent
        }

        Wishlist wishlist = new Wishlist();
        wishlist.setId(new WishlistId(userId,productId));
        wishlist.setUser(userRepo.getReferenceById(userId));
        wishlist.setProduct(productRepo.getReferenceById(productId));
        repo.save(wishlist);
    }

    public void remove(Long userId,Long productId){
        repo.deleteByUserIdAndProductId(userId,productId);
    }
    public List<WishlistProductResponse>getMyWishlist(long userId){
        return repo.findByUserId(userId)
                .stream()
                .map(w-> new WishlistProductResponse(
                        w.getProduct().getId(),
                        w.getProduct().getName(),
                        w.getProduct().getSlug(),
                        w.getProduct().getSummary()
                )).toList();
    }

    public boolean isWishlisted(Long userId, Long productId) {
        return repo.existsByUserIdAndProductId(userId, productId);
    }





}
