package com.conpresp.conprespapi.repository;

import com.conpresp.conprespapi.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PropertyRepository extends JpaRepository<Property, String> {

}
