package com.zimpy.products.Service;

import com.zimpy.catalog.entity.Category;
import com.zimpy.catalog.repository.CategoryRepository;
import com.zimpy.products.dto.CategoryMiniResponse;
import com.zimpy.products.dto.ProductRequest;
import com.zimpy.products.dto.ProductResponse;
import com.zimpy.products.dto.ProductUpdateRequest;
import com.zimpy.products.entity.Product;
import com.zimpy.products.repository.ProductRepository;
import com.zimpy.util.SlugUtil;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import  org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    public ProductService(ProductRepository repository ,CategoryRepository categoryRepository){
        this.productRepository = repository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public ProductResponse createProduct(ProductRequest request){
        Product product = new Product();
        Category category = categoryRepository.findByIdAndDeletedAtIsNull(request.getCategoryId()).orElseThrow(()->new RuntimeException("Category not found with this id "+request.getCategoryId()));
        product.setName(request.getName());
        product.setBrand(request.getBrand());
        product.setSummary(request.getSummary());
        product.setDescription(request.getDescription());
        product.setCategory(category);
        String baseSlug = SlugUtil.toSlug(request.getName());
        String slug = baseSlug;
        int counter = 1;
        while (productRepository.existsBySlugAndDeletedAtIsNull(slug)){
            slug = baseSlug+"-"+counter++;
        }
        product.setSlug(slug);

        Product response = productRepository.save(product);
        return productEntityToResponse(response);
    }

    @Transactional
    public ProductResponse updateProduct(Long id, ProductUpdateRequest request){
        Product product = productRepository
                .findByIdAndDeletedAtIsNull(id)
                .orElseThrow(()-> new RuntimeException(" Product is not found"));
        // category update
        if(request.getCategoryId()!=null){
            Category category = categoryRepository.findByIdAndDeletedAtIsNull(request.getCategoryId()).orElseThrow(()-> new RuntimeException("Categoy not found"));
            product.setCategory(category);
        }
        if(request.getName()!=null && !request.getName().equals(product.getName())){
            product.setName(request.getName());

            String baseSlug = SlugUtil.toSlug(request.getName());
            String slug = baseSlug;
            int counter = 1;
            while (productRepository.existsBySlugAndDeletedAtIsNull(slug)){
                slug = baseSlug+"-"+counter++;
            }
            product.setSlug(slug);

        }

        // Partial Updates
        if(request.getBrand()!=null && !request.getBrand().equals(product.getBrand())){
            product.setBrand(request.getBrand());
        }
        if(request.getSummary()!=null){
            product.setSummary(request.getSummary());
        }
        if(request.getDescription()!=null){
            product.setDescription(request.getDescription());
        }


        Product savedProduct = productRepository.save(product);
        return productEntityToResponse(savedProduct);

    }

    @Transactional
    public void deleteProduct(Long id){
        Product product = productRepository.findByIdAndDeletedAtIsNull(id).orElseThrow(()->new RuntimeException(" product not found with this id : "+ id));
        product.setDeletedAt(LocalDateTime.now());
        productRepository.save(product);
    }


    private ProductResponse productEntityToResponse(Product product){
        Category category = product.getCategory();
        CategoryMiniResponse categoryMiniResponse  = new CategoryMiniResponse(category.getId(), category.getName(), category.getSlug());
        return  new ProductResponse(product.getId(), product.getName(),product.getSlug(),product.getSummary(),product.getDescription(),product.getBrand(),product.isActive(),categoryMiniResponse);
    }

    public Product getAdminProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(()->new RuntimeException("not found"));
        return product;
    }

    public Page<Product> getAllAdminProducts(Pageable pageable) {
        return productRepository.findByDeletedAtIsNull(pageable);
    }

    public void setActive(Long id, boolean b) {
        Product product = productRepository.findByIdAndDeletedAtIsNull(id).orElseThrow(()->new RuntimeException("products not found"));
        product.setActive(b);
        productRepository.save(product);

    }

    public Page<ProductResponse> getAllActiveProducts (Pageable pageable) {
        return productRepository.findByDeletedAtIsNull(pageable).map(this::productEntityToResponse);
    }

    public ProductResponse getActiveProductBySlug(String slug) {
        Product product = productRepository
                .findBySlugAndDeletedAtIsNull(slug)
                .orElseThrow(()->new RuntimeException("product not found with this id"));
        if(!product.isActive()){
            throw new RuntimeException("Product is not active");
        }

        return productEntityToResponse(product);
    }

    public ProductResponse getActiveProductById(Long id) {
        Product product = productRepository.findByIdAndDeletedAtIsNull(id).orElseThrow(()->new RuntimeException("product not found"));

        if(!product.isActive()){
            throw  new RuntimeException("Product is not active");
        }
        return productEntityToResponse(product);
    }

    public Page<ProductResponse> getByCategorySlug(String categorySlug, Pageable pageable) {
        return productRepository.findByCategory_SlugAndDeletedAtIsNull(categorySlug,pageable).map(this::productEntityToResponse);

    }

    public Page<ProductResponse> searchByName(String q, Pageable pageable) {
        return productRepository.findByNameContainingIgnoreCaseAndDeletedAtIsNull(q,pageable).map(this::productEntityToResponse);
    }

    public List<ProductResponse> getSimilarProducts(String slug) {
        Product product = productRepository.findBySlugAndDeletedAtIsNull(slug).orElseThrow(()->new RuntimeException("Product not found"));

        List<Product> similar = productRepository.findTop8ByCategoryAndDeletedAtIsNullAndIdNot(product.getCategory(), product.getId());
        return similar.stream().map(this::productEntityToResponse).toList();
    }

    public Page<ProductResponse> getByBrand(String brand, Pageable pageable) {
        return productRepository.findByBrandIgnoreCaseAndDeletedAtIsNull(brand,pageable).map(this::productEntityToResponse);
    }

    public List<ProductResponse> getLatestProducts() {
        return productRepository.findTop10ByDeletedAtIsNullOrderByCreatedAtDesc().stream().map(this::productEntityToResponse).toList();
    }

    public List<ProductResponse> getPopularProducts() {
        // later: order_count / views
        return productRepository.findTop10ByDeletedAtIsNullOrderByIdDesc()
                .stream().map(this::productEntityToResponse).toList();
    }

    public List<ProductResponse> getFeaturedProducts() {
        // later : featured flag;
        return productRepository.findTop10ByDeletedAtIsNullOrderByCreatedAtDesc().stream().map(this::productEntityToResponse).toList();
    }
}
