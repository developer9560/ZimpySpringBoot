package com.zimpy.productImage.controller;

import com.zimpy.common.ApiResponse;
import com.zimpy.productImage.Service.ProductImageService;
import com.zimpy.productImage.dto.ProductImageResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/public/images")
public class UserProductImageController {
    private final ProductImageService productImageService;

    public UserProductImageController(ProductImageService productImageService) {
        this.productImageService = productImageService;
    }
    @GetMapping("/{productId}/images")
    public ApiResponse<List<ProductImageResponse>>getImages(@PathVariable Long productId) {
        return new  ApiResponse<>(200,"succesfully found ",productImageService.getImages(productId)) ;
    }

}
