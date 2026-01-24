package com.zimpy.attributes.controller;

import com.zimpy.attributes.dto.AttributeValueResponse;
import com.zimpy.attributes.entity.Attribute;
import com.zimpy.attributes.entity.AttributeValue;
import com.zimpy.attributes.services.AttributeValueService;
import com.zimpy.common.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/public/attributeValue")
public class publicAttributeValueController {
    private final AttributeValueService service;

    public publicAttributeValueController(AttributeValueService service) {
        this.service = service;
    }

    @GetMapping("/{attributeId}/values")
    public ResponseEntity<ApiResponse<List<AttributeValueResponse>>> getAll(
            @PathVariable Long attributeId
    ) {
        return ResponseEntity.ok(
                new ApiResponse<>(200, "Found", service.getAll(attributeId))
        );
    }

}
