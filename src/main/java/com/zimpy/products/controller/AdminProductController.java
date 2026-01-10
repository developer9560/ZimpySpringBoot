package com.zimpy.products.controller;

import com.zimpy.products.Service.ProductService;
import com.zimpy.products.dto.ProductRequest;
import com.zimpy.products.dto.ProductResponse;
import com.zimpy.products.dto.ProductUpdateRequest;
import com.zimpy.products.entity.Product;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/admin/products")
@PreAuthorize("hasRole('ADMIN')")
public class AdminProductController {

    private final ProductService productService;
    public AdminProductController(ProductService productService){
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductResponse> create(@Valid @RequestBody ProductRequest request){
        return ResponseEntity.ok(productService.createProduct(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(@PathVariable Long id,@Valid @RequestBody ProductUpdateRequest request){
        return ResponseEntity.ok(productService.updateProduct(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>delete(@PathVariable long id){
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable Long id) {
        return productService.getAdminProductById(id);
    }
    @GetMapping
    public Page<Product> getAll(Pageable pageable) {
        return productService.getAllAdminProducts(pageable);
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<Void> activate(@PathVariable Long id) {
        productService.setActive(id, true);
        return ResponseEntity.noContent().build();
    }
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        productService.setActive(id, false);
        return ResponseEntity.noContent().build();
    }






}
