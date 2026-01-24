package com.zimpy.attributes.controller;

import com.zimpy.attributes.entity.Attribute;
import com.zimpy.attributes.services.AttributeService;
import com.zimpy.common.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/public/attribute")
public class PublicAttributeController {
    private final AttributeService attributeService;
    public PublicAttributeController(AttributeService attributeService) {
        this.attributeService = attributeService;
    }
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<Attribute>>> getAll(){
        List<Attribute> attributeList = attributeService.getAll();
        return ResponseEntity.status(HttpStatus.FOUND).body( new ApiResponse<>(200,"Succesfully Found ",attributeList ));
    }

}
