package com.conpresp.conprespapi.service;

import com.conpresp.conprespapi.dto.PropertyCreateRequest;
import com.conpresp.conprespapi.entity.Property;
import com.conpresp.conprespapi.exception.ResourceCreationException;
import com.conpresp.conprespapi.repository.InstitutionRepository;
import com.conpresp.conprespapi.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private InstitutionRepository institutionRepository;

    public String createProperty(PropertyCreateRequest propertyCreateRequest) throws ResourceCreationException {
        var institution = institutionRepository.findByName(propertyCreateRequest.getHeritageResolutionRequest().getInstitution())
                .orElseThrow(ResourceCreationException::new);

        var createdProperty = propertyRepository.save(propertyCreateRequest.toProperty(institution));
        return createdProperty.getId();
    }

    public Optional<Property> getPropertyById(String uuid)  { return propertyRepository.findById(uuid); }
}
