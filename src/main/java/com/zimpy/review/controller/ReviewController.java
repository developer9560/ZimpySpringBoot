package com.zimpy.review.controller;

import com.zimpy.common.ApiResponse;
import com.zimpy.review.dto.ReviewRequest;
import com.zimpy.review.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

//write/update review
@RestController
@RequestMapping("/reviews")
@PreAuthorize("hasRole('USER')")
public class ReviewController {

    private final ReviewService service;

    public ReviewController(ReviewService service) {
        this.service = service;
    }

    @PostMapping("/product/{productId}")
    public ResponseEntity<ApiResponse<Void>> add(
            Authentication auth,
            @PathVariable Long productId,
            @RequestBody ReviewRequest request
    ) {

        service.addOrUpdate(
                (Long) auth.getPrincipal(),
                productId,
                request
        );
        return ResponseEntity.ok(
                new ApiResponse<>(200, "Review saved", null)

        );
    }
}

