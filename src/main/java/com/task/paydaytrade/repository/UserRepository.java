package com.task.paydaytrade.repository;

import com.task.paydaytrade.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndIsActiveTrue(String email);
}
