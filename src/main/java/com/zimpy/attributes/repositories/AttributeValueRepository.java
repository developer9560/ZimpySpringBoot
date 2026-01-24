package com.zimpy.attributes.repositories;

import com.zimpy.attributes.entity.AttributeValue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttributeValueRepository extends JpaRepository<AttributeValue, Long> {
    List<AttributeValue> findByAttributeId(Long attributeId);

}
