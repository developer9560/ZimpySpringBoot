package com.zimpy.attributes.services;

import com.zimpy.attributes.dto.AttributeResponse;
import com.zimpy.attributes.dto.SkuAttributesResponse;
import com.zimpy.attributes.entity.Attribute;
import com.zimpy.attributes.entity.AttributeValue;
import com.zimpy.attributes.entity.SkuAttributeMap;
import com.zimpy.attributes.repositories.SkuAttributeMapRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublicSkuAttributeService {

    private final SkuAttributeMapRepository mapRepo;

    public PublicSkuAttributeService(SkuAttributeMapRepository mapRepo) {
        this.mapRepo = mapRepo;
    }

    public SkuAttributesResponse getSkuAttributes(Long skuId) {

        List<SkuAttributeMap> mappings = mapRepo.findBySkuId(skuId);

        List<AttributeResponse> attributes = mappings.stream()
                .map(map -> {
                    Attribute attr = map.getAttributeValue().getAttribute();
                    AttributeValue val = map.getAttributeValue();

                    AttributeResponse res = new AttributeResponse();
                    res.setName(attr.getName());
                    res.setType(attr.getType());
                    res.setValue(val.getValue());
                    return res;
                })
                .toList();

        return new SkuAttributesResponse(skuId, attributes);
    }
}
