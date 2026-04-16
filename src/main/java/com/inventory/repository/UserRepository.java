package com.inventory.repository;

import com.inventory.entity.User;
import com.inventory.enums.RoleType;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Find user by username (for login)
    @EntityGraph(attributePaths = {"role"})
    Optional<User> findByUsername(String username);

    // find user by email (unique check / login)
    Optional<User> findByEmail(String email);

    // Check if username already exists
    boolean existsByUsername(String username);

    // Check if email already exists
    boolean existsByEmail(String email);

    // Get all active users
    List<User> findByIsActiveTrue();

    // Get users by role (Admin, Manager, Staff)
    List<User> findByRole_RoleName(RoleType roleName);
}
