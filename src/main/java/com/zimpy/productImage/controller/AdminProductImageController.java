package com.zimpy.productImage.controller;

import com.zimpy.common.ApiResponse;
import com.zimpy.productImage.Service.ProductImageService;
import com.zimpy.productImage.dto.ProductImageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/admin/images")
@PreAuthorize("hasRole('ADMIN')")
public class AdminProductImageController {

    private final ProductImageService productImageService;

    public AdminProductImageController(ProductImageService productImageService){
        this.productImageService = productImageService;

    }

    @PostMapping("/upload/{productId}")
    public ResponseEntity<ApiResponse>uploadImage(
            @PathVariable Long productId,
            @RequestParam("file")MultipartFile file,
            @RequestParam(defaultValue = "false") boolean primary,
            @RequestParam(defaultValue = "0") int sortOrder
            ){
        productImageService.uploadAndSave(productId, file, primary,sortOrder);
        return ResponseEntity.ok(new ApiResponse<>(201,"succesfully uploaded",productImageService.uploadAndSave(productId,file,primary,sortOrder)));
    }

    @PostMapping("/{productId}")
    public ResponseEntity<Void> addImage(
            @PathVariable Long productId,
            @RequestBody ProductImageRequest request
    ) {
        productImageService.addImage(productId, request);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{imageId}/primary")
    public ResponseEntity<Void> setPrimary(@PathVariable Long imageId) {
        productImageService.setPrimary(imageId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{imageId}")
    public ResponseEntity<Void> delete(@PathVariable Long imageId) {
        productImageService.deleteImage(imageId);
        return ResponseEntity.noContent().build();
    }


}
