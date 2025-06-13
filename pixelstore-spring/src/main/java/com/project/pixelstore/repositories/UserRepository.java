package com.project.pixelstore.repositories;

import com.project.pixelstore.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
//    boolean existByPhoneNumber(String phoneNumber);
//    Optional<User> findByPhoneNumber(String phoneNumber);

    boolean existsByPhoneNumber(String phoneNumber);
    Optional<User> findByPhoneNumber(String phoneNumber);
    Optional<User> findByUsername(String username);
}
