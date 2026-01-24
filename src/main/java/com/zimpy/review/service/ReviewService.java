package com.zimpy.review.service;

import com.zimpy.products.repository.ProductRepository;
import com.zimpy.review.dto.ReviewRequest;
import com.zimpy.review.dto.ReviewResponse;
import com.zimpy.review.entity.Review;
import com.zimpy.review.repository.ReviewRepository;
import com.zimpy.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    private final ReviewRepository repo;
    private final ProductRepository productRepo;
    private final UserRepository userRepo;

    public ReviewService(
            ReviewRepository repo,
            ProductRepository productRepo,
            UserRepository userRepo
    ) {
        this.repo = repo;
        this.productRepo = productRepo;
        this.userRepo = userRepo;
    }

    @Transactional
    public void addOrUpdate(
            Long userId,
            Long productId,
            ReviewRequest request
    ) {
        Review review = repo
                .findByUserIdAndProductIdAndDeletedAtIsNull(userId, productId)
                .orElseGet(() -> {
                    Review r = new Review();
                    r.setUser(userRepo.getReferenceById(userId));
                    r.setProduct(productRepo.getReferenceById(productId));
                    return r;
                });

        review.setRating(request.getRating());
        review.setComment(request.getComment());

        repo.save(review);
    }

    public Page<ReviewResponse> getProductReviews(
            Long productId,
            Pageable pageable
    ) {
        return repo
                .findByProductIdAndDeletedAtIsNull(productId, pageable)
                .map(r -> new ReviewResponse(
                        r.getId(),
                        r.getRating(),
                        r.getComment(),
                        r.getUser().getFullName(),
                        r.getCreatedAt()
                ));
    }
    public Double getAverageRating(Long productId) {
        return repo.getAverageRating(productId);
    }
}

