package com.zimpy.attributes.dto;

import com.zimpy.attributes.entity.AttributeType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AttributeRequest {
    @NotBlank
    private String name;
    @NotNull
    private AttributeType type;

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

    public static class AttributeSummaryResponse {
        private Long id;
        private String name;
        private AttributeType type;

        public AttributeSummaryResponse(Long id, String name, AttributeType type) {
            this.id = id;
            this.name = name;
            this.type = type;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
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
    }
}
