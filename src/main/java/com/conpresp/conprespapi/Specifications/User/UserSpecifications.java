package com.conpresp.conprespapi.Specifications.User;

import com.conpresp.conprespapi.entity.user.Profile_;
import com.conpresp.conprespapi.entity.user.User;
import com.conpresp.conprespapi.entity.user.User_;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

public class UserSpecifications {

    public static Specification<User> search(UserSearchCriteria searchCriteria) {
        return searchByName(searchCriteria.getName())
                .and(searchByLastName(searchCriteria.getLastName()))
                .and(searchByEmail(searchCriteria.getEmail()))
                .and(searchByProfile(searchCriteria.getProfile()))
                .and(searchByStatus(searchCriteria.getStatus()));
    }

    public static Specification<User> searchByName(Optional<String> name) {
        return (root, query, criteriaBuilder) -> name.map(firstName -> criteriaBuilder.like(root.get(User_.FIRST_NAME), "%" + name.get() + "%"))
                .orElse(null);
    }

    public static Specification<User> searchByLastName(Optional<String> name) {
        return (root, query, criteriaBuilder) -> name.map(lastName -> criteriaBuilder.like(root.get(User_.LAST_NAME), "%" + name.get() + "%"))
                .orElse(null);
    }

    public static Specification<User> searchByEmail(Optional<String> email) {
        return (root, query, criteriaBuilder) -> email.map(userEmail -> criteriaBuilder.like(root.get(User_.EMAIL), "%" + email.get() + "%"))
                .orElse(null);
    }

    public static Specification<User> searchByProfile(Optional<String> profile) {
        return (root, query, criteriaBuilder) -> profile.map(profileName -> criteriaBuilder.like(root.get(User_.PROFILE).get(Profile_.NAME), "%" + profile.get() + "%"))
                .orElse(null);
    }

    public static Specification<User> searchByStatus(Optional<String> status) {
        return (root, query, criteriaBuilder) -> status.map(userStatus -> criteriaBuilder.equal(root.get(User_.STATUS), status.get()))
                .orElse(null);
    }

}
