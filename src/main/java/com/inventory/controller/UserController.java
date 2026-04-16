package com.inventory.controller;

import com.inventory.dto.UserDTO;
import com.inventory.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // 🔷 Get all users (ADMIN only)
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // 🔷 Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    // 🔷 Assign role
    @PatchMapping("/{id}/role")
    public ResponseEntity<UserDTO> assignRole(@PathVariable Long id,
                                              @RequestParam String roleName) {
        return ResponseEntity.ok(userService.assignRole(id, roleName));
    }

    // 🔷 Deactivate user (soft delete)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deactivateUser(@PathVariable Long id) {
        userService.deactivateUser(id);
        return ResponseEntity.ok("User deactivated");
    }

    // 🔷 Activate user
    @PatchMapping("/{id}/activate")
    public ResponseEntity<UserDTO> activateUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.activateUser(id));
    }
}