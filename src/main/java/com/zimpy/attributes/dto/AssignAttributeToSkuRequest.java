package com.zimpy.attributes.dto;

import java.util.List;

public class AssignAttributeToSkuRequest {
    private Long skuId;
    private List<Long> attributeValueIds;

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public List<Long> getAttributeValueIds() {
        return attributeValueIds;
    }

    public void setAttributeValueIds(List<Long> attributeValueIds) {
        this.attributeValueIds = attributeValueIds;
    }
}
