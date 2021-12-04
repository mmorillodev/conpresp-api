package com.conpresp.conprespapi.Specifications.patrimony;

import com.conpresp.conprespapi.entity.patrimony.*;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.ListJoin;
import java.time.Year;

public class PatrimonySpecifications {

    public static Specification<Patrimony> search(PatrimonySearchCriteria searchCriteria) {
        return searchByDenomination(searchCriteria.getDenomination())
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

    public static Specification<Patrimony> searchByDenomination(String denomination) {
        return (root, query, criteriaBuilder) -> {
            if (denomination == null) return null;

            return criteriaBuilder.like(root.get(Patrimony_.denomination), "%" + denomination + "%");
        };
    }

    public static Specification<Patrimony> searchByResolution(String resolution) {
        return (root, query, criteriaBuilder) -> {
            if (resolution == null) return null;

            ListJoin<Patrimony, HeritageResolution> resolutionJoin = root.join(Patrimony_.heritageResolutions);

            return criteriaBuilder.equal(resolutionJoin.get(HeritageResolution_.resolution), resolution);
        };
    }

    public static Specification<Patrimony> searchByYear(String year) {
        return (root, query, criteriaBuilder) -> {
            if (year == null) return null;

            return criteriaBuilder.equal(root.get(Patrimony_.construction).get(Construction_.constructionYear), Year.parse(year));
        };
    }

    public static Specification<Patrimony> searchByOriginalUsage(String originalUsage) {
        return (root, query, criteriaBuilder) -> {
            if (originalUsage == null) return null;

            return criteriaBuilder.like(root.get(Patrimony_.originalUsage), "%" + originalUsage + "%");
        };
    }

    public static Specification<Patrimony> searchByAddressType(String addressType) {
        return (root, query, criteriaBuilder) -> {
            if (addressType == null) return null;

            return criteriaBuilder.like(root.get(Patrimony_.addressLot).get(AddressLot_.addressType), "%" + addressType + "%");
        };
    }

    public static Specification<Patrimony> searchByAddressTitle(String title) {
        return (root, query, criteriaBuilder) -> {
            if (title == null) return null;

            return criteriaBuilder.like(root.get(Patrimony_.addressLot).get(AddressLot_.title), "%" + title + "%");
        };
    }

    public static Specification<Patrimony> searchByStreet(String street) {
        return (root, query, criteriaBuilder) -> {
            if (street == null) return null;

            return criteriaBuilder.like(root.get(Patrimony_.addressLot).get(AddressLot_.addressStreet), "%" + street + "%");
        };
    }

    public static Specification<Patrimony> searchByAddressNumber(String addressNumber) {
        return (root, query, criteriaBuilder) -> {
            if (addressNumber == null) return null;

            return criteriaBuilder.like(root.get(Patrimony_.addressLot).get(AddressLot_.addressNumber), "%" + addressNumber + "%");
        };
    }

    public static Specification<Patrimony> searchByDistrict(String district) {
        return (root, query, criteriaBuilder) -> {
            if (district == null) return null;

            return criteriaBuilder.like(root.get(Patrimony_.addressLot).get(AddressLot_.addressDistrict), "%" + district + "%");
        };
    }

    public static Specification<Patrimony> searchByRegionalHall(String regionalHall) {
        return (root, query, criteriaBuilder) -> {
            if (regionalHall == null) return null;

            return criteriaBuilder.like(root.get(Patrimony_.addressLot).get(AddressLot_.regionalHall), "%" + regionalHall + "%");
        };
    }

    public static Specification<Patrimony> searchByAuthor(String author) {
        return (root, query, criteriaBuilder) -> {
            if (author == null) return null;

            return criteriaBuilder.like(root.get(Patrimony_.construction).get(Construction_.author), "%" + author + "%");
        };
    }

    public static Specification<Patrimony> searchByArchitecturalStyle(String architecturalStyle) {
        return (root, query, criteriaBuilder) -> {
            if (architecturalStyle == null) return null;

            return criteriaBuilder.like(root.get(Patrimony_.construction).get(Construction_.architecturalStyle), "%" + architecturalStyle + "%");
        };
    }
}