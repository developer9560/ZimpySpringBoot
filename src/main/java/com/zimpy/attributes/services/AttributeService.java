package com.zimpy.attributes.services;

import com.zimpy.attributes.dto.AttributeRequest;
import com.zimpy.attributes.entity.Attribute;
import com.zimpy.attributes.repositories.AttributeRepository;
import com.zimpy.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttributeService {
    private final AttributeRepository repository;
    public AttributeService(AttributeRepository repository){
        this.repository = repository;
    }


    public Attribute create(AttributeRequest request){
        Attribute attribute = new Attribute();
        attribute.setName(request.getName());
        attribute.setType(request.getType());
        return repository.save(attribute);
    }

    public void delete(Long id){
        Attribute attribute = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Attribute not found with id "+ id));
        repository.delete(attribute);
    }

    public Attribute update(Long id, AttributeRequest request){
        Attribute attribute = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Attribute not found with id " + id));
        if(request.getName()!=null){
            if(attribute.getName()!=request.getName()){
                attribute.setName(request.getName());
            }
        }
        if(request.getType()!=null){
            if(attribute.getType()!=request.getType()){
                attribute.setType(request.getType());
            }
        }
        return repository.save(attribute);
    }

    public Attribute getById(long id) {
        Attribute attribute = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Attribute not found with id " + id));
        return attribute;
    }

    public List<Attribute> getAll(){
        return repository.findAll();
    }



}
