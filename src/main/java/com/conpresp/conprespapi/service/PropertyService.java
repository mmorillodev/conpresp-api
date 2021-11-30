package com.conpresp.conprespapi.service;

import com.conpresp.conprespapi.dto.property.request.*;
import com.conpresp.conprespapi.entity.Property;
import com.conpresp.conprespapi.exception.ResourceCreationException;
import com.conpresp.conprespapi.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    public String createProperty(PropertyCreateRequest propertyCreateRequest) {
        var createdProperty = propertyRepository.save(propertyCreateRequest.toProperty());
        return createdProperty.getId();
    }

    public Page<Property> propertyPage(int size) {
        PageRequest pageRequest = PageRequest.of(
          0,
                size,
                Sort.Direction.ASC,
                "name"
        );

        return new PageImpl<>(
                propertyRepository.findAll(),
                pageRequest, size
        );
    }

    public Page<Property> getAllProperty(Pageable pageable) {
        return propertyRepository.findAll(pageable);
    }

    public Optional<Property> getPropertyById(String uuid)  { return propertyRepository.findById(uuid); }

    public void deleteById(String uuid) { propertyRepository.deleteById(uuid); }

    public Property updateProperty(String uuid, PropertyUpdateRequest propertyUpdateRequest) throws ChangeSetPersister.NotFoundException {
        var property = propertyRepository.findById(uuid).orElseThrow(ChangeSetPersister.NotFoundException::new);

        return propertyRepository.save(propertyUpdateRequest.toProperty(property));
    }
}
