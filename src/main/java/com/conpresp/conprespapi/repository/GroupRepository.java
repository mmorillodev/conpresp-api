package com.conpresp.conprespapi.repository;

import com.conpresp.conprespapi.entity.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<UserGroup, Long> {
    Optional<UserGroup> findByName(String s);
}
