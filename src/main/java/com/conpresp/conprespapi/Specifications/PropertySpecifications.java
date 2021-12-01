package com.conpresp.conprespapi.Specifications;

import com.conpresp.conprespapi.entity.AddressLot_;
import com.conpresp.conprespapi.entity.Construction_;
import com.conpresp.conprespapi.entity.Property;
import com.conpresp.conprespapi.entity.Property_;
import com.conpresp.conprespapi.exception.InvalidOperatorException;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.time.Year;

public class PropertySpecifications {

    public static Specification<Property> searchByDesignation(String designation) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(Property_.DESIGNATION), "%" + designation + "%"));
    }

    public static Specification<Property> searchByYear(Year year, char operator) {
        if (operator == '>') {
            return ((root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get(Property_.CONSTRUCTION).get(Construction_.CONSTRUCTION_YEAR), year));
        } else if (operator == '=') {
            return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Property_.CONSTRUCTION).get(Construction_.CONSTRUCTION_YEAR), year));
        } else {
            return ((root, query, criteriaBuilder) -> criteriaBuilder.lessThan(root.get(Property_.CONSTRUCTION).get(Construction_.CONSTRUCTION_YEAR), year));
        }
    }

    public static Specification<Property> searchByOriginalUsage(String originalUsage) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(Property_.ORIGINAL_USAGE), "%" + originalUsage + "%"));
    }

    public static Specification<Property> searchByAddressType(String addressType) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(Property_.ADDRESS_LOT).get(AddressLot_.ADDRESS_TYPE), "%" + addressType + "%"));
    }

    public static Specification<Property> searchByAddressTitle(String title) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(Property_.ADDRESS_LOT).get(AddressLot_.TITLE), "%" + title + "%"));
    }

    public static Specification<Property> searchByStreet(String street) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(Property_.ADDRESS_LOT).get(AddressLot_.ADDRESS_STREET), "%" + street + "%"));
    }

    public static Specification<Property> searchByAddressNumber(String addressNumber) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(Property_.ADDRESS_LOT).get(AddressLot_.ADDRESS_NUMBER), "%" + addressNumber + "%"));
    }

    public static Specification<Property> searchByDistrict(String district) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(Property_.ADDRESS_LOT).get(AddressLot_.ADDRESS_DISTRICT), "%" + district + "%"));
    }

    public static Specification<Property> searchByRegionalHall(String regionalHall) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(Property_.ADDRESS_LOT).get(AddressLot_.REGIONAL_HALL), "%" + regionalHall + "%"));
    }

    public static Specification<Property> searchByAuthor(String author) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(Property_.AUTHOR), "%" + author + "%"));
    }

    public static Specification<Property> searchByArchitecturalStyle(String architecturalStyle) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(Property_.CONSTRUCTION).get(Construction_.ARCHITECTURAL_STYLE), "%" + architecturalStyle + "%"));
    }
}
