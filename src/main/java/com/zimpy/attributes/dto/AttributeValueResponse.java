package com.zimpy.attributes.dto;

public class AttributeValueResponse {
    private Long valueId;
    private String value;
    private AttributeRequest.AttributeSummaryResponse attribute;

    public AttributeValueResponse(Long valueId, String value, AttributeRequest.AttributeSummaryResponse attribute) {
        this.valueId = valueId;
        this.value = value;
        this.attribute = attribute;
    }

    public Long getValueId() {
        return valueId;
    }

    public void setValueId(Long valueId) {
        this.valueId = valueId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public AttributeRequest.AttributeSummaryResponse getAttribute() {
        return attribute;
    }

    public void setAttribute(AttributeRequest.AttributeSummaryResponse attribute) {
        this.attribute = attribute;
    }
}
