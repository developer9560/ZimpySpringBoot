package com.zimpy.inventory.Service;

import com.zimpy.exception.BadRequestException;
import com.zimpy.exception.ResourceNotFoundException;
import com.zimpy.inventory.dto.InventoryResponse;
import com.zimpy.inventory.entity.Inventory;
import com.zimpy.inventory.repository.InventoryRepository;
import com.zimpy.productsku.entity.ProductSku;
import com.zimpy.productsku.repository.ProductSkuRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {
    private final InventoryRepository inventoryRepo;
    private final ProductSkuRepository skuRepo;

    public InventoryService(InventoryRepository inventoryRepo, ProductSkuRepository skuRepo) {
        this.inventoryRepo = inventoryRepo;
        this.skuRepo = skuRepo;

    }

    @Transactional
    public InventoryResponse addStock(Long skuId, int quantity) {

        if (quantity <= 0) {
            throw new BadRequestException("Stock must be positive");
        }

        ProductSku sku = skuRepo.findById(skuId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("SKU not found with id: " + skuId)
                );

        Inventory inventory = inventoryRepo.findBySkuId(skuId)
                .orElseGet(() -> {
                    Inventory inv = new Inventory();
                    inv.setSku(sku);
                    inv.setTotalStock(0);
                    inv.setReservedStock(0);
                    inv.setAvailableStock(0);
                    return inventoryRepo.save(inv);
                });

        inventory.setTotalStock(
                inventory.getTotalStock() + quantity
        );

        inventory.setAvailableStock(
                inventory.getTotalStock() - inventory.getReservedStock()
        );

        inventoryRepo.save(inventory);
        return toResponse(inventory);
    }

    public InventoryResponse getBySku(Long skuId) {
        Inventory inventory = inventoryRepo.findBySkuId(skuId)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found"));
        return toResponse(inventory);
    }

    @Transactional
    public InventoryResponse setStock(Long skuId, int totalStock) {

        if (totalStock < 0) {
            throw new BadRequestException("Stock cannot be negative");
        }

        Inventory inventory = inventoryRepo.findBySkuId(skuId)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found"));

        if (totalStock < inventory.getReservedStock()) {
            throw new BadRequestException("Total stock cannot be less than reserved stock");
        }

        inventory.setTotalStock(totalStock);
        inventory.setAvailableStock(totalStock - inventory.getReservedStock());

        return toResponse(inventory);
    }

    public InventoryResponse getAvailability(Long skuId) {

        Inventory inventory = inventoryRepo.findBySkuId(skuId)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found"));

        return toResponse(inventory);
    }


    @Transactional
    public void reserveStock(Long skuId, int quantity) {

        if (quantity <= 0) {
            throw new BadRequestException("Quantity must be positive");
        }

        Inventory inventory = inventoryRepo.findBySkuIdForUpdate(skuId)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found"));

        if (inventory.getAvailableStock() < quantity) {
            throw new BadRequestException("Insufficient stock available");
        }

        inventory.setReservedStock(
                inventory.getReservedStock() + quantity
        );

        inventory.setAvailableStock(
                inventory.getTotalStock() - inventory.getReservedStock()
        );
    }

    @Transactional
    public void releaseStock(Long skuId, int quantity) {

        if (quantity <= 0) {
            throw new BadRequestException("Quantity must be positive");
        }

        Inventory inventory = inventoryRepo.findBySkuIdForUpdate(skuId)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found"));

        if (inventory.getReservedStock() < quantity) {
            throw new BadRequestException("Release quantity exceeds reserved stock");
        }

        inventory.setReservedStock(
                inventory.getReservedStock() - quantity
        );

        inventory.setAvailableStock(
                inventory.getTotalStock() - inventory.getReservedStock()
        );
    }

    @Transactional
    public void deductStock(Long skuId, int quantity) {

        if (quantity <= 0) {
            throw new BadRequestException("Quantity must be positive");
        }

        Inventory inventory = inventoryRepo.findBySkuIdForUpdate(skuId)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found"));

        if (inventory.getReservedStock() < quantity) {
            throw new BadRequestException("Not enough reserved stock to deduct");
        }

        inventory.setTotalStock(
                inventory.getTotalStock() - quantity
        );

        inventory.setReservedStock(
                inventory.getReservedStock() - quantity
        );

        inventory.setAvailableStock(
                inventory.getTotalStock() - inventory.getReservedStock()
        );
    }

    public void delete(Long skuId){
        List<Inventory> inventories = inventoryRepo.findListSkuId(skuId);
        inventoryRepo.deleteAll(inventories);
    }






    private InventoryResponse toResponse(Inventory inventory) {
        InventoryResponse res = new InventoryResponse();
        res.setSkuId(inventory.getSku().getId());
        res.setTotalStock(inventory.getTotalStock());
        res.setReservedStock(inventory.getReservedStock());
        res.setAvailableStock(inventory.getAvailableStock());
        return res;
    }
}
