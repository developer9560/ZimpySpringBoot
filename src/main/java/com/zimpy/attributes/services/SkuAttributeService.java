package com.zimpy.attributes.services;

import com.zimpy.attributes.dto.AssignAttributeToSkuRequest;
import com.zimpy.attributes.entity.AttributeValue;
import com.zimpy.attributes.entity.SkuAttributeMap;
import com.zimpy.attributes.repositories.AttributeValueRepository;
import com.zimpy.attributes.repositories.SkuAttributeMapRepository;
import com.zimpy.exception.ResourceNotFoundException;
import com.zimpy.productsku.entity.ProductSku;
import com.zimpy.productsku.repository.ProductSkuRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class SkuAttributeService {
    private final ProductSkuRepository skuRepo;
    private final AttributeValueRepository valueRepo;
    private final SkuAttributeMapRepository mapRepo;

    public SkuAttributeService(
            ProductSkuRepository skuRepo,
            AttributeValueRepository valueRepo,
            SkuAttributeMapRepository mapRepo
    ) {
        this.skuRepo = skuRepo;
        this.valueRepo = valueRepo;
        this.mapRepo = mapRepo;
    }

    @Transactional
    public void assigAttributes(AssignAttributeToSkuRequest request){

        ProductSku sku = skuRepo.findById(request.getSkuId()).orElseThrow(()-> new ResourceNotFoundException("SKU not found"));

        for(Long valueId : request.getAttributeValueIds()){
            AttributeValue val = valueRepo.findById(valueId)
                    .orElseThrow(()->new ResourceNotFoundException("Attribute value not found"));
            SkuAttributeMap maped = new SkuAttributeMap();
            maped.setAttributeValue(val);
            maped.setSku(sku);
            mapRepo.save( maped);
        }

    }
}
