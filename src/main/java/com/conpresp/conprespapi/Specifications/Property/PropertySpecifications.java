package com.conpresp.conprespapi.Specifications.Property;

import com.conpresp.conprespapi.entity.property.*;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.ListJoin;
import javax.swing.text.html.Option;
import java.time.Year;
import java.util.Optional;

public class PropertySpecifications {

    public static Specification<Property> search(PropertySearchCriteria searchCriteria) {
        return searchByDesignation(searchCriteria.getDesignation()).and(searchByResolution(searchCriteria.getResolution())).and(searchByYear(searchCriteria.getConstructionYear())).and(searchByStreet(searchCriteria.getStreet())).and(searchByOriginalUsage(searchCriteria.getOriginalUsage())).and(searchByAddressType(searchCriteria.getAddressType())).and(searchByAddressTitle(searchCriteria.getAddressTitle())).and(searchByAddressNumber(searchCriteria.getAddressNumber())).and(searchByDistrict(searchCriteria.getDistrict())).and(searchByRegionalHall(searchCriteria.getRegionalHall())).and(searchByAuthor(searchCriteria.getAuthor())).and(searchByArchitecturalStyle(searchCriteria.getArchitecturalStyle()));
    }

    public static Specification<Property> searchByDesignation(Optional<String> designation) {
        return (root, query, criteriaBuilder) -> designation.map(name -> criteriaBuilder.like(root.get(Property_.designation), "%" + designation.get() + "%")).orElse(null);
    }

    public static Specification<Property> searchByResolution(Optional<String> resolution) {
        return (root, query, criteriaBuilder) -> {
            ListJoin<Property, PropertyResolution> resolutionJoin = root.join(Property_.propertyResolutions);
            return resolution.map(resolutionName -> criteriaBuilder.equal(resolutionJoin.get(PropertyResolution_.resolution), resolution.get()))
                    .orElse(null);
        };
    }

    public static Specification<Property> searchByYear(Optional<String> year) {
        return (root, query, criteriaBuilder) -> year.map(constructionYear -> criteriaBuilder.equal(root.get(Property_.construction).get(Construction_.constructionYear), Year.parse(year.get()))).orElse(null);
    }

    public static Specification<Property> searchByOriginalUsage(Optional<String> originalUsage) {
        return (root, query, criteriaBuilder) -> originalUsage.map(originalUse -> criteriaBuilder.like(root.get(Property_.originalUsage), "%" + originalUsage.get() + "%")).orElse(null);
    }

    public static Specification<Property> searchByAddressType(Optional<String> addressType) {
        return (root, query, criteriaBuilder) -> addressType.map(address -> criteriaBuilder.like(root.get(Property_.addressLot).get(AddressLot_.addressType), "%" + addressType.get() + "%")).orElse(null);
    }

    public static Specification<Property> searchByAddressTitle(Optional<String> title) {
        return (root, query, criteriaBuilder) -> title.map(addressTitle -> criteriaBuilder.like(root.get(Property_.addressLot).get(AddressLot_.title), "%" + title.get() + "%")).orElse(null);
    }

    public static Specification<Property> searchByStreet(Optional<String> street) {
        return (root, query, criteriaBuilder) -> street.map(addressStreet -> criteriaBuilder.like(root.get(Property_.addressLot).get(AddressLot_.addressStreet), "%" + street.get() + "%")).orElse(null);
    }

    public static Specification<Property> searchByAddressNumber(Optional<String> addressNumber) {
        return (root, query, criteriaBuilder) -> addressNumber.map(number -> criteriaBuilder.like(root.get(Property_.addressLot).get(AddressLot_.addressNumber), "%" + addressNumber.get() + "%")).orElse(null);
    }

    public static Specification<Property> searchByDistrict(Optional<String> district) {
        return (root, query, criteriaBuilder) -> district.map(addressDistrict -> criteriaBuilder.like(root.get(Property_.addressLot).get(AddressLot_.addressDistrict), "%" + district.get() + "%")).orElse(null);
    }

    public static Specification<Property> searchByRegionalHall(Optional<String> regionalHall) {
        return (root, query, criteriaBuilder) -> regionalHall.map(hall -> criteriaBuilder.like(root.get(Property_.addressLot).get(AddressLot_.regionalHall), "%" + regionalHall.get() + "%")).orElse(null);
    }

    public static Specification<Property> searchByAuthor(Optional<String> author) {
        return (root, query, criteriaBuilder) -> author.map(constructionAuthor -> criteriaBuilder.like(root.get(Property_.construction).get(Construction_.author), "%" + author.get() + "%")).orElse(null);
    }

    public static Specification<Property> searchByArchitecturalStyle(Optional<String> architecturalStyle) {
        return (root, query, criteriaBuilder) -> architecturalStyle.map(style -> criteriaBuilder.like(root.get(Property_.construction).get(Construction_.architecturalStyle), "%" + architecturalStyle.get() + "%")).orElse(null);
    }
}
