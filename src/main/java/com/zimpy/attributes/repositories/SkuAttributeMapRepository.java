package com.zimpy.attributes.repositories;

import com.zimpy.attributes.entity.SkuAttributeMap;
import com.zimpy.attributes.entity.SkuAttributeMapId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SkuAttributeMapRepository extends JpaRepository<SkuAttributeMap , SkuAttributeMapId> {
    List<SkuAttributeMap> findBySkuId(long skuId);
}
