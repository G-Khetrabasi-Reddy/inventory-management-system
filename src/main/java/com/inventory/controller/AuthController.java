package com.inventory.controller;

import com.inventory.dto.*;
import com.inventory.service.AuthService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    // 🔷 Register User
    @PostMapping("/register")
    public AuthResponseDTO register(@Valid @RequestBody RegisterDTO dto) {
        return authService.register(dto);
    }

    // 🔷 Login User
    @PostMapping("/login")
    public AuthResponseDTO login(@Valid @RequestBody LoginRequestDTO dto) {
        return authService.login(dto);
    }
}