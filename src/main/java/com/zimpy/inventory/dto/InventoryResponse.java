package com.zimpy.inventory.dto;

public class InventoryResponse {
    private Long skuId;
    private int totalStock;
    private  int reservedStock;
    private int availableStock;

    public InventoryResponse(){

    }
    public InventoryResponse(Long skuId, int totalStock, int reservedStock, int availableStock) {
        this.skuId = skuId;
        this.totalStock = totalStock;
        this.reservedStock = reservedStock;
        this.availableStock = availableStock;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public int getTotalStock() {
        return totalStock;
    }

    public void setTotalStock(int totalStock) {
        this.totalStock = totalStock;
    }

    public int getAvailableStock() {
        return availableStock;
    }

    public void setAvailableStock(int availableStock) {
        this.availableStock = availableStock;
    }

    public int getReservedStock() {
        return reservedStock;
    }

    public void setReservedStock(int reservedStock) {
        this.reservedStock = reservedStock;
    }
}
