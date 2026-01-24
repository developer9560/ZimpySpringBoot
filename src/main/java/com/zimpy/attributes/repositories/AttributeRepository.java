package com.zimpy.attributes.repositories;

import com.zimpy.attributes.entity.Attribute;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttributeRepository extends JpaRepository<Attribute,Long> {
}
