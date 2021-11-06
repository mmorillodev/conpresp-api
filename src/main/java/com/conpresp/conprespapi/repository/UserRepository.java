package com.conpresp.conprespapi.repository;

import com.conpresp.conprespapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
