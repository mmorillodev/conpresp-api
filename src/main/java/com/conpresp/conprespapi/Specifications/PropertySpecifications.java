package com.conpresp.conprespapi.Specifications;

import com.conpresp.conprespapi.entity.AddressLot_;
import com.conpresp.conprespapi.entity.Construction_;
import com.conpresp.conprespapi.entity.Property;
import com.conpresp.conprespapi.entity.Property_;
import org.springframework.data.jpa.domain.Specification;

import java.time.Year;

public class PropertySpecifications {

    public static Specification<Property> search(PropertySearchCriteria searchCriteria) {
        return searchByDesignation(searchCriteria.getDesignation())
                .or(searchByYear(searchCriteria.getConstructionYear()))
                .or(searchByStreet(searchCriteria.getStreet()))
                .or(searchByOriginalUsage(searchCriteria.getOriginalUsage()))
                .or(searchByAddressType(searchCriteria.getAddressType()))
                .or(searchByAddressTitle(searchCriteria.getAddressTitle()))
                .or(searchByAddressNumber(searchCriteria.getAddressNumber()))
                .or(searchByDistrict(searchCriteria.getDistrict()))
                .or(searchByRegionalHall(searchCriteria.getRegionalHall()))
                .or(searchByAuthor(searchCriteria.getAuthor()))
                .or(searchByArchitecturalStyle(searchCriteria.getArchitecturalStyle()));
    }

    public static Specification<Property> searchByDesignation(String designation) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(Property_.DESIGNATION), "%" + designation + "%");
    }

    public static Specification<Property> searchByYear(String year) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Property_.CONSTRUCTION).get(Construction_.CONSTRUCTION_YEAR), Year.parse(year));
    }

    public static Specification<Property> searchByOriginalUsage(String originalUsage) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(Property_.ORIGINAL_USAGE), "%" + originalUsage + "%");
    }

    public static Specification<Property> searchByAddressType(String addressType) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(Property_.ADDRESS_LOT).get(AddressLot_.ADDRESS_TYPE), "%" + addressType + "%");
    }

    public static Specification<Property> searchByAddressTitle(String title) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(Property_.ADDRESS_LOT).get(AddressLot_.TITLE), "%" + title + "%");
    }

    public static Specification<Property> searchByStreet(String street) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(Property_.ADDRESS_LOT).get(AddressLot_.ADDRESS_STREET), "%" + street + "%");
    }

    public static Specification<Property> searchByAddressNumber(String addressNumber) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(Property_.ADDRESS_LOT).get(AddressLot_.ADDRESS_NUMBER), "%" + addressNumber + "%");
    }

    public static Specification<Property> searchByDistrict(String district) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(Property_.ADDRESS_LOT).get(AddressLot_.ADDRESS_DISTRICT), "%" + district + "%");
    }

    public static Specification<Property> searchByRegionalHall(String regionalHall) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(Property_.ADDRESS_LOT).get(AddressLot_.REGIONAL_HALL), "%" + regionalHall + "%");
    }

    public static Specification<Property> searchByAuthor(String author) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(Property_.AUTHOR), "%" + author + "%");
    }

    public static Specification<Property> searchByArchitecturalStyle(String architecturalStyle) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(Property_.CONSTRUCTION).get(Construction_.ARCHITECTURAL_STYLE), "%" + architecturalStyle + "%");
    }
}
