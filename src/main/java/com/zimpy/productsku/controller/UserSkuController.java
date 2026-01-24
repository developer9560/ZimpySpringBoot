package com.zimpy.productsku.controller;

import com.zimpy.common.ApiResponse;
import com.zimpy.productsku.dto.ProductSkuResponse;
import com.zimpy.productsku.services.ProductSkuService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/public/sku")

public class UserSkuController {
    public final ProductSkuService service;
    public  UserSkuController(ProductSkuService service){
        this.service = service;
    }
    @GetMapping("/{productId}/get")
    public ApiResponse<List<ProductSkuResponse> >get(@PathVariable Long productId){
        return new ApiResponse<>(200,"success",service.getAllSku(productId));
    }

}

