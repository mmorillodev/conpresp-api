package com.conpresp.conprespapi.service;

import com.conpresp.conprespapi.dto.property.request.PropertyCreateRequest;
import com.conpresp.conprespapi.entity.Property;
import com.conpresp.conprespapi.exception.ResourceCreationException;
import com.conpresp.conprespapi.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    public String createProperty(PropertyCreateRequest propertyCreateRequest) {
        var createdProperty = propertyRepository.save(propertyCreateRequest.toProperty());
        return createdProperty.getId();
    }

    public Optional<Property> getPropertyById(String uuid)  { return propertyRepository.findById(uuid); }
}
