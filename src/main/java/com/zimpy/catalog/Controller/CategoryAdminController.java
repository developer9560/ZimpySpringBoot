package com.zimpy.catalog.Controller;

import com.zimpy.catalog.Service.CategoryService;
import com.zimpy.catalog.dto.CategoryRequest;
import com.zimpy.catalog.dto.CategoryResponse;
import com.zimpy.catalog.dto.UpdateRequest;
import com.zimpy.catalog.entity.Category;
import com.zimpy.common.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/admin/categories")
@PreAuthorize("hasRole('ADMIN')")
public class CategoryAdminController {

    private final CategoryService categoryService;

    public CategoryAdminController(CategoryService categoryService) {
        this.categoryService = categoryService;

    }

    @PostMapping
    public ResponseEntity<ApiResponse<CategoryResponse>> create(@Valid @RequestPart("data") CategoryRequest request,
            @RequestPart(value = "imageUrl", required = false) MultipartFile file) {
        return ResponseEntity
                .ok(new ApiResponse<>(200, "SuccessFull Created", categoryService.createCategory(request, file)));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{slug}")
    private ResponseEntity<String> deletebyslug(@PathVariable String slug) {
        categoryService.deleteCategoryBySlug(slug);
        return ResponseEntity.ok("successfully deleted");
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> update(@PathVariable Long id, @Valid @RequestBody UpdateRequest request,
            @RequestPart(value = "imageUrl", required = false) MultipartFile file) {
        return ResponseEntity.ok(categoryService.updateCategory(id, request, file));
    }

    @PutMapping(value = "/{slug}", consumes = org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CategoryResponse> updateBySlug(
            @PathVariable String slug,
            @Valid @RequestPart("category") UpdateRequest request,
            @RequestPart(value = "imageUrl", required = false) MultipartFile file) {
        return ResponseEntity.ok(categoryService.updateCategoryBySlug(slug, request, file));
    }

}
