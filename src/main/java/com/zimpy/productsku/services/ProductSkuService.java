package com.zimpy.productsku.services;

import com.zimpy.exception.ResourceNotFoundException;
import com.zimpy.products.entity.Product;
import com.zimpy.products.repository.ProductRepository;
import com.zimpy.productsku.dto.ProductSkuRequest;
import com.zimpy.productsku.dto.ProductSkuResponse;
import com.zimpy.productsku.entity.ProductSku;
import com.zimpy.productsku.repository.ProductSkuRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ProductSkuService {
    private static long count = 100;
    private final ProductSkuRepository productSkuRepository;
    private final ProductRepository productRepository;

    public ProductSkuService(
            ProductRepository productRepository,
            ProductSkuRepository productSkuRepository
    ){
        this.productRepository = productRepository;
        this.productSkuRepository = productSkuRepository;
    }
    private String generateSkuCode(Product product) {
        return "ZMP-SKU-" + product.getId() + "-" +
                UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }


    public ProductSkuResponse addSku(ProductSkuRequest request){
        Product product = productRepository.findByIdAndDeletedAtIsNull(request.getProductId())
                .orElseThrow(()-> new ResourceNotFoundException("product not found"));
        if(request.getMrp().compareTo(BigDecimal.ZERO)<0|| request.getPrice().compareTo(BigDecimal.ZERO)<0){
            throw new ResourceNotFoundException("value cannot be negative");

        }else if(request.getPrice().compareTo(request.getMrp())>0){
            throw  new ResourceNotFoundException("price cannot be greate than mrp");
        }

        String skuCode = generateSkuCode(product);
        ProductSku sku = new ProductSku();
        sku.setProduct(product);
        sku.setPrice(request.getPrice());
        sku.setSkuCode(skuCode);
        sku.setMrp(request.getMrp());
       ProductSku saved =  productSkuRepository.save(sku);

        return new ProductSkuResponse(saved.getId(),saved.getSkuCode(),saved.getPrice(),saved.getMrp(),saved.getProduct().getId());

    }
    @Transactional
    public void deleteSku(Long id) {
        ProductSku sku = productSkuRepository
                .findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("SKU not found"));

        sku.setDeletedAt(LocalDateTime.now());
    }

    public ProductSkuResponse update(long id , ProductSkuRequest request) {
        ProductSku productSku = productSkuRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(()->new ResourceNotFoundException("not found"));

        if(request.getMrp()!=null){
            productSku.setMrp(request.getMrp());

        }
        if(request.getPrice()!=null){
            productSku.setPrice(request.getPrice());
        }
        if(productSku.getProduct().getId() != request.getProductId()){
            if(request.getProductId()!=null){
                Product product = productRepository.findByIdAndDeletedAtIsNull(request.getProductId())
                                .orElseThrow(()->new ResourceNotFoundException("not found product id"));
                productSku.setProduct(product);
                productSku.setSkuCode(generateSkuCode(product));

            }else{
                throw new ResourceNotFoundException("Product Id missing");

            }
        }
        ProductSku saved = productSkuRepository.save(productSku);
        return new ProductSkuResponse(saved.getId(),saved.getSkuCode(),saved.getPrice(),saved.getMrp(),saved.getProduct().getId());

    }


    public List<ProductSkuResponse> getAllSku(Long productId){
        List<ProductSku> productSkuList = productSkuRepository.findByProduct_IdAndDeletedAtIsNullOrderByCreatedAtDesc(productId);
        return  productSkuList.stream()
                .map(productSku ->
                        new ProductSkuResponse
                                (
                                        productSku.getId(),
                                        productSku.getSkuCode(),
                                        productSku.getPrice(),
                                        productSku.getMrp(),
                                        productSku.getProduct().getId()
                                )
                ).toList();

    }


}
