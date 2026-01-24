package com.zimpy.catalog.Service;

import com.zimpy.catalog.dto.CategoryRequest;
import com.zimpy.catalog.dto.CategoryResponse;
import com.zimpy.catalog.dto.CategoryTreeResponse;
import com.zimpy.catalog.dto.UpdateRequest;
import com.zimpy.catalog.entity.Category;
import com.zimpy.catalog.repository.CategoryRepository;
import com.zimpy.util.ImageUploadService;
import com.zimpy.util.SlugUtil;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ImageUploadService imageUploadService;

    public CategoryService(CategoryRepository categoryRepository, ImageUploadService imageUploadService) {
        this.categoryRepository = categoryRepository;
        this.imageUploadService = imageUploadService;

    }

    public CategoryResponse createCategory(CategoryRequest request, MultipartFile file) {

        Category category = new Category();
        category.setName(request.getName());
        if (file != null) {
            String url = imageUploadService.uploadImage(file);
            category.setImageUrl(url);
        }

        // slug generation
        String baseSlug = SlugUtil.toSlug(request.getName());
        String slug = baseSlug;
        int counter = 1;
        while (categoryRepository.existsBySlugAndDeletedAtIsNull(slug)) {
            slug = baseSlug + "-" + counter++;
        }
        category.setSlug(slug);

        // parent and level calculation

        if (request.getParentId() == null) {
            category.setLevel(0);
        } else {
            Category parent = categoryRepository.findByIdAndDeletedAtIsNull(request.getParentId())
                    .orElseThrow(() -> new RuntimeException("Parent category not found"));
            category.setParent(parent);
            category.setLevel(parent.getLevel() + 1);

        }
        Category saved = categoryRepository.save(category);
        return entityToResponse(saved);
    }

    private CategoryResponse entityToResponse(Category category) {
        return new CategoryResponse(category.getId(), category.getName(), category.getSlug(), category.getLevel(),
                category.getImageUrl());
    }

    private CategoryTreeResponse buildTree(Category category) {
        List<CategoryTreeResponse> children = category.getChildren()
                .stream()
                .filter(child -> child.getDeletedAt() == null)
                .map(this::buildTree) // recursive call update below? No, this calls buildTree which is this method.
                .toList();
        return new CategoryTreeResponse(
                category.getId(),
                category.getName(),
                category.getSlug(),
                category.getLevel(),
                category.getImageUrl(),
                children);
    }

    // ... public getCategoryTree ...

    // ... inside mapToResponse ...
    private CategoryResponse mapToResponse(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getSlug(),
                category.getLevel(),
                category.getImageUrl());
    }

    public List<CategoryTreeResponse> getCategoryTree() {
        List<Category> rootCategories = categoryRepository.findByParentIsNullAndDeletedAtIsNull();
        return rootCategories.stream().map(this::buildTree).toList();
    }

    @Transactional
    public CategoryResponse updateCategory(Long id, UpdateRequest request, MultipartFile file) {
        // fetch current category
        Category category = categoryRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // handle name update ( slug regeneraion)
        if (request.getName() != null && !request.getName().equals(category.getName())) {
            category.setName(request.getName());
            String baseSlug = SlugUtil.toSlug(request.getName());
            String slug = baseSlug;
            int counter = 1;
            while (categoryRepository.existsBySlugAndDeletedAtIsNull(slug)) {
                slug = baseSlug + "-" + counter++;
            }
            category.setSlug(slug);
        }
        if (file != null) {
            String url = imageUploadService.uploadImage(file);
            category.setImageUrl(url);
        }
        // handle parent update
        if (request.getParentId() != null) {
            // self-parent check
            if (request.getParentId().equals(category.getId())) {
                throw new RuntimeException("Category cannot be its own parent");
            }

            Category newParent = categoryRepository
                    .findByIdAndDeletedAtIsNull(request.getParentId())
                    .orElseThrow(() -> new RuntimeException("Parent category not found"));

            // cylce prevention
            if (isDescendant(category, newParent)) {
                throw new RuntimeException("Cannot move category under its own descendant");
            }

            // update parent
            category.setParent(newParent);
            int newLevel = newParent.getLevel() + 1;
            int levelDiff = newLevel - category.getLevel();
            category.setLevel(newLevel);
            updateChildrenLevels(category, levelDiff);

        }
        categoryRepository.save(category);
        return mapToResponse(category);
    }

    public CategoryResponse updateCategoryBySlug(String slug, UpdateRequest request, MultipartFile file) {
        // fetch current category
        Category category = categoryRepository.findBySlugAndDeletedAtIsNull(slug)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        if (file != null) {
            String url = imageUploadService.uploadImage(file);
            category.setImageUrl(url);
        }

        // handle name update ( slug regeneraion)
        if (request.getName() != null && !request.getName().equals(category.getName())) {
            category.setName(request.getName());
            String baseSlug = SlugUtil.toSlug(request.getName());
            String newSlug = baseSlug;
            int counter = 1;
            while (categoryRepository.existsBySlugAndDeletedAtIsNull(slug)) {
                newSlug = baseSlug + "-" + counter++;
            }
            category.setSlug(slug);
        }
        // handle parent update
        if (request.getParentId() != null) {
            // self-parent check
            if (request.getParentId().equals(category.getId())) {
                throw new RuntimeException("Category cannot be its own parent");
            }

            Category newParent = categoryRepository
                    .findByIdAndDeletedAtIsNull(request.getParentId())
                    .orElseThrow(() -> new RuntimeException("Parent category not found"));

            // cylce prevention
            if (isDescendant(category, newParent)) {
                throw new RuntimeException("Cannot move category under its own descendant");
            }

            // update parent
            category.setParent(newParent);
            int newLevel = newParent.getLevel() + 1;
            int levelDiff = newLevel - category.getLevel();
            category.setLevel(newLevel);
            updateChildrenLevels(category, levelDiff);

        }
        categoryRepository.save(category);
        return mapToResponse(category);
    }

    private boolean isDescendant(Category root, Category candindateParent) {
        Category parent = candindateParent.getParent();
        while (parent != null) {
            if (parent.getId().equals(root.getId())) {
                return true;
            }
            parent = parent.getParent();
        }
        return false;
    }

    private void updateChildrenLevels(Category parent, int levelDiff) {
        List<Category> children = categoryRepository.findByParent(parent);
        for (Category child : children) {
            child.setLevel(child.getLevel() + levelDiff);
            categoryRepository.save(child);
            updateChildrenLevels(child, levelDiff);
        }
    }

//    private CategoryResponse mapToResponse(Category category) {
//        return new CategoryResponse(
//                category.getId(),
//                category.getName(),
//                category.getSlug(),
//                category.getLevel(),
//                category.getImageUrl());
//    }

    public String deleteCategory(Long id) {
        // fetch active category
        Category category = categoryRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        // check active children( block delete)

        boolean hasChildren = categoryRepository.existsByParentAndDeletedAtIsNull(category);

        if (hasChildren) {
            throw new RuntimeException("Cannot deleted category with active sub-categories");
        }
        category.setDeletedAt(LocalDateTime.now());
        categoryRepository.save(category);

        return "Successfully Deleted";
    }

    public String deleteCategoryBySlug(String slug) {
        // fetch active category
        Category category = categoryRepository.findBySlugAndDeletedAtIsNull(slug)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        // check active children( block delete)

        boolean hasChildren = categoryRepository.existsByParentAndDeletedAtIsNull(category);

        if (hasChildren) {
            throw new RuntimeException("Cannot deleted category with active sub-categories");
        }
        category.setDeletedAt(LocalDateTime.now());
        categoryRepository.save(category);

        return "Successfully Deleted";
    }

    public CategoryResponse findById(Long id) {
        Category category = categoryRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new RuntimeException("category not found "));
        return mapToResponse(category);
    }

}
