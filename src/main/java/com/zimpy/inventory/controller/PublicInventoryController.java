package com.zimpy.inventory.controller;

import com.zimpy.common.ApiResponse;
import com.zimpy.inventory.Service.InventoryService;
import com.zimpy.inventory.dto.InventoryResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public/inventory")
public class PublicInventoryController {
    private final InventoryService service;
    public PublicInventoryController(InventoryService service) {
        this.service = service;
    }

    @GetMapping("/skus/{skuId}/availability")
    public ResponseEntity<ApiResponse<InventoryResponse>> availability(
            @PathVariable Long skuId
    ){
        return ResponseEntity.ok(
                new ApiResponse<>(200, "Availability fetched",
                        service.getAvailability(skuId))
        );
    }

}
