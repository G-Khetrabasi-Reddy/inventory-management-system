package com.inventory.service.impl;

import com.inventory.dto.*;
import com.inventory.entity.Role;
import com.inventory.entity.User;
import com.inventory.enums.RoleType;
import com.inventory.exceptions.InvalidCredentialsException;
import com.inventory.exceptions.UserAlreadyExistsException;
import com.inventory.mapper.UserMapper;
import com.inventory.repository.RoleRepository;
import com.inventory.repository.UserRepository;
import com.inventory.security.JwtUtil;
import com.inventory.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    // ✅ FIX FOR ISSUE 5: Inject AuthenticationManager
    @Autowired
    private AuthenticationManager authenticationManager;

    // 🔷 REGISTER
    @Override
    @Transactional
    public AuthResponseDTO register(RegisterDTO dto) {

        // Validate duplicates
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new UserAlreadyExistsException("Username already exists");
        }

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new UserAlreadyExistsException("Email already exists");
        }

        // Get role (default STAFF if null)
        RoleType roleType = (dto.getRole() != null)
                ? RoleType.valueOf(dto.getRole().toUpperCase())
                : RoleType.STAFF;

        Role role = roleRepository.findByRoleName(roleType)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        // Convert DTO → Entity
        User user = UserMapper.toEntity(dto, role);

        // 🔥 Encrypt password
        user.setPasswordHash(passwordEncoder.encode(dto.getPassword()));

        // Save user
        user = userRepository.save(user);

        // Generate token
        String token = jwtUtil.generateToken(user.getUsername(), user.getRole().getRoleName().name());

        return UserMapper.toAuthResponse(user, token);
    }

    // 🔷 LOGIN
    @Override
    @Transactional
    public AuthResponseDTO login(LoginRequestDTO dto) {

        // ✅ FIX FOR ISSUE 5: Delegate authentication to Spring Security
        // This leverages Spring Security's features (bad credentials events, locked accounts, etc.)
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword())
        );

        // If authentication passes, fetch the user to get additional details
        User user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid username"));

        if (!user.getIsActive()) {
            throw new RuntimeException("User is inactive");
        }

        // 🔥 Update last login
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        // Generate token
        String token = jwtUtil.generateToken(user.getUsername(), user.getRole().getRoleName().name());

        return UserMapper.toAuthResponse(user, token);
    }

    // 🔷 VALIDATE (internal use)
    @Override
    public void validateUser(String username, String password) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new InvalidCredentialsException("Invalid user"));

        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            throw new InvalidCredentialsException("Invalid credentials");
        }
    }
}