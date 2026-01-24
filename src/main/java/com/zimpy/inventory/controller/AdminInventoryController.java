package com.zimpy.inventory.controller;

import com.zimpy.common.ApiResponse;
import com.zimpy.inventory.Service.InventoryService;
import com.zimpy.inventory.dto.InventoryResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/inventory")
@PreAuthorize("hasRole('ADMIN')")
public class AdminInventoryController {

    private final InventoryService service;

    public AdminInventoryController(InventoryService service) {
        this.service = service;
    }

    @PatchMapping("/skus/{skuId}/add")
    public ResponseEntity<ApiResponse<InventoryResponse>> addStock(
            @PathVariable Long skuId,
            @RequestParam int quantity
    ) {
        return ResponseEntity.ok(
                new ApiResponse<>(200, "Stock added",
                        service.addStock(skuId, quantity))
        );

    }

    @GetMapping("/skus/{skuId}")
    public ResponseEntity<ApiResponse<InventoryResponse>> getInventory(
            @PathVariable Long skuId
    ) {
        return ResponseEntity.ok(
                new ApiResponse<>(200, "Inventory fetched",
                        service.getBySku(skuId))
        );
    }

    @PutMapping("/skus/{skuId}/set")
    public ResponseEntity<ApiResponse<InventoryResponse>> setStock(
            @PathVariable Long skuId,
            @RequestParam int quantity
    ) {
        return ResponseEntity.ok(
                new ApiResponse<>(200, "Stock set successfully",
                        service.setStock(skuId, quantity))
        );
    }

    @DeleteMapping("/skus/{skuId}")
    public ResponseEntity<ApiResponse<Void> >delet(@PathVariable Long skuId){
        service.delete(skuId);
        return ResponseEntity.ok(new ApiResponse<>(200,"deleted all inventory with sku id "+skuId, null));
    }

}
