package com.zimpy.attributes.dto;

import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

public class SkuAttributesResponse {
    private Long skuId;
    private List<AttributeResponse> attributes;

    public SkuAttributesResponse(Long skuId, List<AttributeResponse> attributes) {
        this.skuId = skuId;
        this.attributes = attributes;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public List<AttributeResponse> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<AttributeResponse> attributes) {
        this.attributes = attributes;
    }
}
