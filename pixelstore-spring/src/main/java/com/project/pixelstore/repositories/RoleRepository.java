package com.project.pixelstore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.pixelstore.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
//    Role findById(Long id);
}
