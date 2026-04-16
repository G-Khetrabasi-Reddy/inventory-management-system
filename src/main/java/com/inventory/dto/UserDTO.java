package com.inventory.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserDTO {

    private Long userId;
    private String username;
    private String email;
    @Pattern(regexp = "ADMIN|MANAGER|STAFF", message = "Invalid role")
    private String role;
    private Boolean isActive;
}