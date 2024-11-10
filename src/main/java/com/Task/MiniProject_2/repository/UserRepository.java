package com.Task.MiniProject_2.repository;

import com.Task.MiniProject_2.entity.securityEntity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsernameOrEmail(String username, String email);
}

