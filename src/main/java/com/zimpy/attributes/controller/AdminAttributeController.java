package com.zimpy.attributes.controller;

import com.zimpy.attributes.dto.AssignAttributeToSkuRequest;
import com.zimpy.attributes.dto.AttributeRequest;
import com.zimpy.attributes.entity.Attribute;
import com.zimpy.attributes.services.AttributeService;
import com.zimpy.attributes.services.SkuAttributeService;
import com.zimpy.common.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/attributes")
@PreAuthorize("hasRole('ADMIN')")
public class AdminAttributeController {
    private final AttributeService attributeService;

    private final SkuAttributeService skuService;


    public AdminAttributeController(AttributeService attributeService ,
                                    SkuAttributeService skuService
    ) {
        this.attributeService = attributeService;
        this.skuService = skuService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Attribute>> create(@Valid @RequestBody AttributeRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(201,"Succesfully created ", attributeService.create(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Attribute>> update(@PathVariable Long id ,@Valid @RequestBody AttributeRequest request){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ApiResponse<>(200,"Successfully updated ", attributeService.update(id,request)));
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        attributeService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Attribute>> get(@PathVariable Long id){
        return ResponseEntity.ok().body(new ApiResponse<>(200,"Succesfully found", attributeService.getById(id)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Attribute>>> getAll(){
        List<Attribute> attributeList = attributeService.getAll();
        return ResponseEntity.status(HttpStatus.FOUND).body( new ApiResponse<>(200,"Succesfully Found ",attributeList ));
    }
    // for assign attribuetosku , attributeMap

    @PostMapping("/assign")
    public void assign(@Valid @RequestBody AssignAttributeToSkuRequest request){
        skuService.assigAttributes(request);
    }


}
