package com.zimpy.catalog.Controller;

import com.zimpy.catalog.Service.CategoryService;
import com.zimpy.catalog.dto.CategoryResponse;
import com.zimpy.catalog.dto.CategoryTreeResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryUserController {
    private final CategoryService service;
    public CategoryUserController(CategoryService categoryService){
        service=categoryService;
    }
    @GetMapping("/tree")
    public List<CategoryTreeResponse> getTree(){
        return service.getCategoryTree();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse>findbyId(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }
}
