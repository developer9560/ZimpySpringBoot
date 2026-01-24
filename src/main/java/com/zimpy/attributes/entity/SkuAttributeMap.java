package com.zimpy.attributes.entity;

import com.zimpy.productsku.entity.ProductSku;
import jakarta.persistence.*;

@IdClass(SkuAttributeMapId.class)
@Entity
@Table(name = "sku_attribute_map")

public class SkuAttributeMap {
    @Id
    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "sku_id")
    private ProductSku sku;


    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attribute_value_id")
    private AttributeValue attributeValue;

    public AttributeValue getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(AttributeValue attributeValue) {
        this.attributeValue = attributeValue;
    }

    public ProductSku getSku() {
        return sku;
    }

    public void setSku(ProductSku sku) {
        this.sku = sku;
    }
}
