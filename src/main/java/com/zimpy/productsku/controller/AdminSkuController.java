package com.zimpy.productsku.controller;

import com.zimpy.productsku.dto.ProductSkuRequest;
import com.zimpy.productsku.dto.ProductSkuResponse;
import com.zimpy.productsku.services.ProductSkuService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/skus")
@PreAuthorize("hasRole('ADMIN')")
public class AdminSkuController {
    private final ProductSkuService service;

    public AdminSkuController(ProductSkuService service)
    {
        this.service  = service;
    }
    @PostMapping("/add")
    public ProductSkuResponse addSku(@Valid @RequestBody ProductSkuRequest request){
        return service.addSku(request);
    }
    @DeleteMapping("/{id}")
    public void Delete(@PathVariable long id){
        service.deleteSku(id);
    }
    @PutMapping("/{id}")
    public ProductSkuResponse update(@PathVariable long id, ProductSkuRequest request){
        return  service.update(id, request);
    }



}
