package com.conpresp.conprespapi.Specifications.Property;

import com.conpresp.conprespapi.entity.property.*;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.ListJoin;
import java.time.Year;
import java.util.Optional;

public class PropertySpecifications {

    public static Specification<Property> search(PropertySearchCriteria searchCriteria) {
        return searchByDesignation(searchCriteria.getDesignation())
                .and(searchByResolution(searchCriteria.getResolution()))
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

    public static Specification<Property> searchByDesignation(String designation) {
        return (root, query, criteriaBuilder) -> {
            if (designation == null) return null;

            return criteriaBuilder.like(root.get(Property_.designation), "%" + designation + "%");
        };
    }

    public static Specification<Property> searchByResolution(String resolution) {
        return (root, query, criteriaBuilder) -> {
            if (resolution == null) return null;

            ListJoin<Property, PropertyResolution> resolutionJoin = root.join(Property_.propertyResolutions);

            return criteriaBuilder.equal(resolutionJoin.get(PropertyResolution_.resolution), resolution);
        };
    }

    public static Specification<Property> searchByYear(String year) {
        return (root, query, criteriaBuilder) -> {
            if (year == null) return null;

            return criteriaBuilder.equal(root.get(Property_.construction).get(Construction_.constructionYear), Year.parse(year));
        };
    }

    public static Specification<Property> searchByOriginalUsage(String originalUsage) {
        return (root, query, criteriaBuilder) -> {
            if (originalUsage == null) return null;

            return criteriaBuilder.like(root.get(Property_.originalUsage), "%" + originalUsage + "%");
        };
    }

    public static Specification<Property> searchByAddressType(String addressType) {
        return (root, query, criteriaBuilder) -> {
            if (addressType == null) return null;

            return criteriaBuilder.like(root.get(Property_.addressLot).get(AddressLot_.addressType), "%" + addressType + "%");
        };
    }

    public static Specification<Property> searchByAddressTitle(String title) {
        return (root, query, criteriaBuilder) -> {
            if (title == null) return null;

            return criteriaBuilder.like(root.get(Property_.addressLot).get(AddressLot_.title), "%" + title + "%");
        };
    }

    public static Specification<Property> searchByStreet(String street) {
        return (root, query, criteriaBuilder) -> {
            if (street == null) return null;

            return criteriaBuilder.like(root.get(Property_.addressLot).get(AddressLot_.addressStreet), "%" + street + "%");
        };
    }

    public static Specification<Property> searchByAddressNumber(String addressNumber) {
        return (root, query, criteriaBuilder) -> {
            if (addressNumber == null) return null;

            return criteriaBuilder.like(root.get(Property_.addressLot).get(AddressLot_.addressNumber), "%" + addressNumber + "%");
        };
    }

    public static Specification<Property> searchByDistrict(String district) {
        return (root, query, criteriaBuilder) -> {
            if (district == null) return null;

            return criteriaBuilder.like(root.get(Property_.addressLot).get(AddressLot_.addressDistrict), "%" + district + "%");
        };
    }

    public static Specification<Property> searchByRegionalHall(String regionalHall) {
        return (root, query, criteriaBuilder) -> {
            if (regionalHall == null) return null;

            return criteriaBuilder.like(root.get(Property_.addressLot).get(AddressLot_.regionalHall), "%" + regionalHall + "%");
        };
    }

    public static Specification<Property> searchByAuthor(String author) {
        return (root, query, criteriaBuilder) -> {
            if (author == null) return null;

            return criteriaBuilder.like(root.get(Property_.construction).get(Construction_.author), "%" + author + "%");
        };
    }

    public static Specification<Property> searchByArchitecturalStyle(String architecturalStyle) {
        return (root, query, criteriaBuilder) -> {
            if (architecturalStyle == null) return null;

            return criteriaBuilder.like(root.get(Property_.construction).get(Construction_.architecturalStyle), "%" + architecturalStyle + "%");
        };
    }
}
