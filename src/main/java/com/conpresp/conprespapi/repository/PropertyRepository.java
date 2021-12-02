package com.conpresp.conprespapi.repository;

import com.conpresp.conprespapi.entity.property.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PropertyRepository extends JpaRepository<Property, String>, JpaSpecificationExecutor<Property> {

}
