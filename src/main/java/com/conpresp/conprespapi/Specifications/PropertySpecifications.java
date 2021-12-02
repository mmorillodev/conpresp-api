package com.conpresp.conprespapi.Specifications;

import com.conpresp.conprespapi.entity.property.AddressLot_;
import com.conpresp.conprespapi.entity.property.Construction_;
import com.conpresp.conprespapi.entity.property.Property;
import com.conpresp.conprespapi.entity.property.Property_;
import org.springframework.data.jpa.domain.Specification;

import java.time.Year;
import java.util.Optional;

public class PropertySpecifications {

    public static Specification<Property> search(PropertySearchCriteria searchCriteria) {
        return searchByDesignation(searchCriteria.getDesignation())
                .and(searchByYear(searchCriteria.getConstructionYear()))
                .and(searchByStreet(searchCriteria.getStreet()))
                .and(searchByOriginalUsage(searchCriteria.getOriginalUsage()))
                .and(searchByAddressType(searchCriteria.getAddressType()))
                .and(searchByAddressTitle(searchCriteria.getAddressTitle()))
                .and(searchByAddressNumber(searchCriteria.getAddressNumber()))
                .and(searchByDistrict(searchCriteria.getDistrict()))
                .and(searchByRegionalHall(searchCriteria.getRegionalHall()))
                .and(searchByAuthor(searchCriteria.getAuthor()))
                .and(searchByArchitecturalStyle(searchCriteria.getArchitecturalStyle()));
    }

    public static Specification<Property> searchByDesignation(Optional<String> designation) {
        return (root, query, criteriaBuilder) -> designation.map(name -> criteriaBuilder.like(root.get(Property_.DESIGNATION), "%" + designation.get() + "%"))
                .orElse(null);
    }

    public static Specification<Property> searchByYear(Optional<String> year) {
        return (root, query, criteriaBuilder) -> year.map(constructionYear -> criteriaBuilder.equal(root.get(Property_.CONSTRUCTION).get(Construction_.CONSTRUCTION_YEAR), Year.parse(year.get())))
                .orElse(null);
    }

    public static Specification<Property> searchByOriginalUsage(Optional<String> originalUsage) {
        return (root, query, criteriaBuilder) -> originalUsage.map(originalUse -> criteriaBuilder.like(root.get(Property_.ORIGINAL_USAGE), "%" + originalUsage.get() + "%"))
                .orElse(null);
    }

    public static Specification<Property> searchByAddressType(Optional<String> addressType) {
        return (root, query, criteriaBuilder) -> addressType.map(address -> criteriaBuilder.like(root.get(Property_.ADDRESS_LOT).get(AddressLot_.ADDRESS_TYPE), "%" + addressType.get() + "%"))
                .orElse(null);
    }

    public static Specification<Property> searchByAddressTitle(Optional<String> title) {
        return (root, query, criteriaBuilder) -> title.map(addressTitle -> criteriaBuilder.like(root.get(Property_.ADDRESS_LOT).get(AddressLot_.TITLE), "%" + title.get() + "%"))
                .orElse(null);
    }

    public static Specification<Property> searchByStreet(Optional<String> street) {
        return (root, query, criteriaBuilder) -> street.map(addressStreet -> criteriaBuilder.like(root.get(Property_.ADDRESS_LOT).get(AddressLot_.ADDRESS_STREET), "%" + street.get() + "%"))
                .orElse(null);
    }

    public static Specification<Property> searchByAddressNumber(Optional<String> addressNumber) {
        return (root, query, criteriaBuilder) -> addressNumber.map(number -> criteriaBuilder.like(root.get(Property_.ADDRESS_LOT).get(AddressLot_.ADDRESS_NUMBER), "%" + addressNumber.get() + "%"))
                .orElse(null);
    }

    public static Specification<Property> searchByDistrict(Optional<String> district) {
        return (root, query, criteriaBuilder) -> district.map(addressDistrict -> criteriaBuilder.like(root.get(Property_.ADDRESS_LOT).get(AddressLot_.ADDRESS_DISTRICT), "%" + district.get() + "%"))
                .orElse(null);
    }

    public static Specification<Property> searchByRegionalHall(Optional<String> regionalHall) {
        return (root, query, criteriaBuilder) -> regionalHall.map(hall -> criteriaBuilder.like(root.get(Property_.ADDRESS_LOT).get(AddressLot_.REGIONAL_HALL), "%" + regionalHall.get() + "%"))
                .orElse(null);
    }

    public static Specification<Property> searchByAuthor(Optional<String> author) {
        return (root, query, criteriaBuilder) -> author.map(constructionAuthor -> criteriaBuilder.like(root.get(Property_.AUTHOR), "%" + author.get() + "%"))
                .orElse(null);
    }

    public static Specification<Property> searchByArchitecturalStyle(Optional<String> architecturalStyle) {
        return (root, query, criteriaBuilder) -> architecturalStyle.map(style -> criteriaBuilder.like(root.get(Property_.CONSTRUCTION).get(Construction_.ARCHITECTURAL_STYLE), "%" + architecturalStyle.get() + "%"))
                .orElse(null);
    }
}
