package com.zimpy.attributes.controller;

import com.zimpy.attributes.dto.SkuAttributesResponse;
import com.zimpy.attributes.services.PublicSkuAttributeService;
import com.zimpy.common.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public/products/skus")
public class PublicSkuAttributeController {

    private final PublicSkuAttributeService service;

    public PublicSkuAttributeController(PublicSkuAttributeService service) {
        this.service = service;
    }

    @GetMapping("/{skuId}/attributes")
    public ResponseEntity<ApiResponse<SkuAttributesResponse>> getSkuAttributes(
            @PathVariable Long skuId
    ) {
        return ResponseEntity.ok(
                new ApiResponse<>(200, "Attributes fetched", service.getSkuAttributes(skuId))
        );
    }
}
