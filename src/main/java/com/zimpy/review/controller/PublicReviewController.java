package com.zimpy.review.controller;

import com.zimpy.common.ApiResponse;
import com.zimpy.review.dto.ReviewResponse;
import com.zimpy.review.service.ReviewService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public/reviews")
public class PublicReviewController {

    private final ReviewService service;

    public PublicReviewController(ReviewService service) {
        this.service = service;
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ApiResponse<Page<ReviewResponse>>> get(
            @PathVariable Long productId,
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                new ApiResponse<>(
                        200,
                        "Reviews fetched",
                        service.getProductReviews(productId, pageable)
                )
        );
    }

    @GetMapping("/product/{productId}/rating")
    public ResponseEntity<ApiResponse<Double>> rating(
            @PathVariable Long productId
    ) {
        return ResponseEntity.ok(
                new ApiResponse<>(
                        200,
                        "Average rating",
                        service.getAverageRating(productId)
                )
        );
    }
}
