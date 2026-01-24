package com.zimpy.attributes.controller;

import com.zimpy.attributes.dto.AttributeValueRequest;
import com.zimpy.attributes.entity.AttributeValue;
import com.zimpy.attributes.services.AttributeValueService;
import com.zimpy.common.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/attributeValue")
@PreAuthorize("hasRole('ADMIN')")
public class AdminAttributeValueController {

    private final AttributeValueService service;

    public AdminAttributeValueController(AttributeValueService service) {
        this.service = service;
    }
    @PostMapping("/create")
    public ApiResponse<AttributeValue> create(@Valid @RequestBody AttributeValueRequest request)
    {
        return new ApiResponse<>(201,"Successfully Created", service.create(request));
    }
    @PutMapping("/update/{id}")
    public ApiResponse<AttributeValue> update(@PathVariable Long id, @Valid @RequestBody AttributeValueRequest request)
    {
        return new ApiResponse<>(200,"Successfully Updated", service.update(id,request));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id)
    {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
