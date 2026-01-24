package com.zimpy.products.controller;

import com.zimpy.products.Service.ProductService;
import com.zimpy.products.dto.ProductResponse;
import com.zimpy.products.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public/products")
public class UserProductController {
    private final ProductService productService;

    public UserProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping
    public Page<ProductResponse> getAll(Pageable pageable) {
        return productService.getAllActiveProducts(pageable);
    }

    @GetMapping("/{slug}")
    public ProductResponse getBySlug(@PathVariable String slug) {
        return productService.getActiveProductBySlug(slug);
    }
    @GetMapping("/id/{id}")
    public ProductResponse getById(@PathVariable Long id) {
        return productService.getActiveProductById(id);
    }
    @GetMapping(params = "category")
    public Page<ProductResponse> getByCategory(
            @RequestParam String category,
            Pageable pageable
    ) {
        return productService.getByCategorySlug(category, pageable);
    }

    @GetMapping("/search")
    public Page<ProductResponse> search(
            @RequestParam String q,
            Pageable pageable
    ) {
        return productService.searchByName(q, pageable);
    }

    @GetMapping("/{slug}/similar")
    public List<ProductResponse> similar(@PathVariable String slug) {
        return productService.getSimilarProducts(slug);
    }

    @GetMapping("/brand/{brand}")
    public Page<ProductResponse> byBrand(
            @PathVariable String brand,
            Pageable pageable
    ) {
        return productService.getByBrand(brand, pageable);
    }
    @GetMapping("/latest")
    public List<ProductResponse> latest() {
        return productService.getLatestProducts();
    }

    @GetMapping("/popular")
    public List<ProductResponse> popular() {
        return productService.getPopularProducts();
    }
    @GetMapping("/featured")
    public List<ProductResponse> featured() {
        return productService.getFeaturedProducts();
    }



}
