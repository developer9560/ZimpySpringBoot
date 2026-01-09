package com.zimpy.catalog.Controller;

import com.zimpy.catalog.Service.CategoryService;
import com.zimpy.catalog.dto.CategoryRequest;
import com.zimpy.catalog.dto.CategoryResponse;
import com.zimpy.catalog.dto.UpdateRequest;
import com.zimpy.catalog.entity.Category;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/categories")
@PreAuthorize("hasRole('ADMIN')")
public class CategoryAdminController {

    private final CategoryService categoryService;

    public CategoryAdminController(CategoryService categoryService){
        this.categoryService = categoryService;
    }
    @PostMapping
    public Category create(@Valid @RequestBody CategoryRequest request) {
        return categoryService.createCategory(request);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{slug}")
    private ResponseEntity<String> deletebyslug(@PathVariable String slug){
        categoryService.deleteCategoryBySlug(slug);
        return ResponseEntity.ok("successfully deleted");
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse>update(@PathVariable Long id , @Valid @RequestBody UpdateRequest request){
        return ResponseEntity.ok(categoryService.updateCategory(id,request));
    }

    @PutMapping("/{Slug}")
    public ResponseEntity<CategoryResponse>updateBySlug(@PathVariable String slug, @Valid @RequestBody UpdateRequest request){
        return ResponseEntity.ok(categoryService.updateCategoryBySlug(slug,request));
    }


}
