package com.zimpy.productImage.Service;

import com.zimpy.exception.ResourceNotFoundException;
import com.zimpy.productImage.dto.ProductImageRequest;
import com.zimpy.productImage.dto.ProductImageResponse;
import com.zimpy.productImage.entity.ProductImage;
import com.zimpy.productImage.repository.ProductImageRepository;
import com.zimpy.products.entity.Product;
import com.zimpy.products.repository.ProductRepository;
import com.zimpy.util.ImageUploadService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ProductImageService {
    private final ProductImageRepository imageRepository;
    private final ProductRepository productRepository;
    private final ImageUploadService imageUploadService;
    public ProductImageService(ProductImageRepository repository, ProductRepository productRepository, ImageUploadService imageUploadService)

    {
        this.imageUploadService = imageUploadService;
        this.imageRepository = repository;
        this.productRepository = productRepository;
    }

    @Transactional
    public ProductImageResponse addImage(Long productId, ProductImageRequest request){

        Product product = productRepository.findByIdAndDeletedAtIsNull(productId)
                .orElseThrow(()-> new ResourceNotFoundException("Product not found with this "+ productId));
        if(request.isPrimary()){
            imageRepository.findByProductAndPrimaryImageTrue(product)
                    .ifPresent(img->img.setPrimaryImage(false));
        }
        ProductImage image = new ProductImage();
        image.setProduct(product);
        image.setImageUrl(request.getImageUrl());
        image.setPrimaryImage(request.isPrimary());
        image.setSortOrder(request.getSortOrder());
       ProductImage savedImage =  imageRepository.save(image);
       return ImageEntityToResponse(savedImage);


    }
    @Transactional
    public void deleteImage(Long imageId){
        ProductImage image = imageRepository.findById(imageId).orElseThrow(()->new ResourceNotFoundException("Image not found"));
        imageRepository.delete(image);

    }
    public List<ProductImageResponse> getImages(Long productId){
        Product product = productRepository.findByIdAndDeletedAtIsNull(productId)
                .orElseThrow(()-> new ResourceNotFoundException("Product not found"));
        return imageRepository.findByProductOrderBySortOrderAsc(product)
                .stream().map(this::ImageEntityToResponse).toList();
    }

    public void setPrimary(Long imageId){
        ProductImage image = imageRepository.findById(imageId)
                .orElseThrow(()-> new ResourceNotFoundException("Image not found"));
        Product product = image.getProduct();
        imageRepository.findByProductAndPrimaryImageTrue(product)
                .ifPresent(img->img.setPrimaryImage(false));

        image.setPrimaryImage(true);
        // i think here should be save method;
    }


    private ProductImageResponse ImageEntityToResponse(ProductImage productImage){

        return new ProductImageResponse(productImage.getId(), productImage.isPrimaryImage(), productImage.getSortOrder(), productImage.getImageUrl(),productImage.getProduct().getId());
    }


    @Transactional
    public ProductImageResponse uploadAndSave(Long productId, MultipartFile file, boolean primary, int sortOrder) {

        Product product = productRepository.findByIdAndDeletedAtIsNull(productId)
                .orElseThrow(()-> new ResourceNotFoundException("Product not found"));

        String imageUrl = imageUploadService.uploadImage(file);

        if(primary) {
            imageRepository.findByProductAndPrimaryImageTrue(product)
                    .ifPresent(img-> img.setPrimaryImage(false));

        }
        ProductImage image = new ProductImage();
        image.setProduct(product);
        image.setImageUrl(imageUrl);
        image.setPrimaryImage(primary);
        image.setSortOrder(sortOrder);

        ProductImage saved  = imageRepository.save(image);
        return ImageEntityToResponse(saved);
    }
}
