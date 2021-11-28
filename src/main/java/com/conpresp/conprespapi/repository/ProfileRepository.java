package com.conpresp.conprespapi.repository;

import com.conpresp.conprespapi.entity.user.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByName(String s);
}
