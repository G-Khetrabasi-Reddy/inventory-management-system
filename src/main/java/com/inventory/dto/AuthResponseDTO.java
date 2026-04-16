package com.inventory.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponseDTO {

    private String token;     // JWT token (future)
    private String username;
    @Pattern(regexp = "ADMIN|MANAGER|STAFF", message = "Invalid role")
    private String role;
}
