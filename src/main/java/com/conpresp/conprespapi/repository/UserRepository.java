package com.conpresp.conprespapi.repository;

import com.conpresp.conprespapi.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String s);
}
