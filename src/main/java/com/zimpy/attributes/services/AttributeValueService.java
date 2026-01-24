package com.zimpy.attributes.services;

import com.zimpy.attributes.dto.AttributeRequest;
import com.zimpy.attributes.dto.AttributeValueRequest;
import com.zimpy.attributes.dto.AttributeValueResponse;
import com.zimpy.attributes.entity.Attribute;
import com.zimpy.attributes.entity.AttributeValue;
import com.zimpy.attributes.repositories.AttributeRepository;
import com.zimpy.attributes.repositories.AttributeValueRepository;
import com.zimpy.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttributeValueService {
    private final AttributeRepository attributeRepo;
    private final AttributeValueRepository valueRepo;

    public AttributeValueService(
            AttributeRepository attributeRepo,
            AttributeValueRepository valueRepo
    ) {
        this.attributeRepo = attributeRepo;
        this.valueRepo = valueRepo;
    }

    public AttributeValue create(AttributeValueRequest request){
        Attribute attribute = attributeRepo.findById(request.getAttributeId()).orElseThrow(()-> new ResourceNotFoundException("Attribute not Found with this id"+request.getAttributeId()));
        AttributeValue attributeValue = new AttributeValue();
        attributeValue.setAttribute(attribute);
        attributeValue.setValue(request.getValue());
        return valueRepo.save(attributeValue);
    }
    public AttributeValue update(Long id, AttributeValueRequest request){
        AttributeValue attributeValue = valueRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("attributevalue not foudn with this id"+id));
        if(request.getValue()!=null){
            if(attributeValue.getValue()!=request.getValue()){
                attributeValue.setValue(request.getValue());
            }
        }
        if(request.getAttributeId()!=null){
            Attribute attribute = attributeRepo.findById(request.getAttributeId())
                    .orElseThrow(()-> new ResourceNotFoundException("Attribute Id not found with this id "+request.getAttributeId()));
            attributeValue.setAttribute(attribute);
        }
        return valueRepo.save(attributeValue);

    }
    public List<AttributeValueResponse> getAll(Long attributeId) {

        List<AttributeValue> values = valueRepo.findByAttributeId(attributeId);

        return values.stream()
                .map(v -> new AttributeValueResponse(
                        v.getId(),
                        v.getValue(),
                        new AttributeRequest.AttributeSummaryResponse(
                                v.getAttribute().getId(),
                                v.getAttribute().getName(),
                                v.getAttribute().getType()
                        )
                ))
                .toList();
    }


    public void delete(long id){
        AttributeValue attributeValue = valueRepo.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("not found attributeValue with this id"));
        valueRepo.delete(attributeValue);
    }


}
