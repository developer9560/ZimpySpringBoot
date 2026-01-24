package com.zimpy.attributes.dto;

import com.zimpy.attributes.entity.AttributeType;

public class AttributeResponse {
    private String name;
    private AttributeType type;
    private String value;

    public AttributeResponse(){

    }
    public AttributeResponse(String name, AttributeType type, String value) {
        this.name = name;
        this.type = type;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AttributeType getType() {
        return type;
    }

    public void setType(AttributeType type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
