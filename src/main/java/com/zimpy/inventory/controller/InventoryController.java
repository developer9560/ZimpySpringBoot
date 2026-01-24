package com.zimpy.inventory.controller;

import com.zimpy.common.ApiResponse;
import com.zimpy.inventory.Service.InventoryService;
import com.zimpy.inventory.dto.ReserveStockRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory/skus")
public class InventoryController {

    private final InventoryService service;

    public InventoryController(InventoryService service) {
        this.service = service;
    }

    @PostMapping("/{skuId}/reserve")
    public ResponseEntity<ApiResponse<Void>> reserve(
            @PathVariable Long skuId,
            @RequestBody ReserveStockRequest request
    ) {
        service.reserveStock(skuId, request.getQuantity());
        return ResponseEntity.ok(
                new ApiResponse<>(200, "Stock reserved", null)
        );
    }

    @PostMapping("/{skuId}/release")
    public ResponseEntity<ApiResponse<Void>> release(
            @PathVariable Long skuId,
            @RequestBody ReserveStockRequest request
    ) {
        service.releaseStock(skuId, request.getQuantity());
        return ResponseEntity.ok(
                new ApiResponse<>(200, "Stock released", null)
        );
    }

    @PostMapping("/{skuId}/deduct")
    public ResponseEntity<ApiResponse<Void>> deduct(
            @PathVariable Long skuId,
            @RequestBody ReserveStockRequest request
    ) {
        service.deductStock(skuId, request.getQuantity());
        return ResponseEntity.ok(
                new ApiResponse<>(200, "Stock deducted", null)
        );
    }
}

