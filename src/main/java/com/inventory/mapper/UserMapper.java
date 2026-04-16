package com.inventory.mapper;

import com.inventory.dto.AuthResponseDTO;
import com.inventory.dto.RegisterDTO;
import com.inventory.dto.UserDTO;
import com.inventory.entity.Role;
import com.inventory.entity.User;

public class UserMapper {

    // 🔷 Convert RegisterDTO → User Entity
    public static User toEntity(RegisterDTO dto, Role role) {

        User user = new User();

        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());

        user.setRole(role);
        user.setIsActive(true);

        return user;
    }

    // 🔷 Convert User Entity → UserDTO (safe response)
    public static UserDTO toDTO(User user) {

        UserDTO dto = new UserDTO();

        dto.setUserId(user.getUserId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());

        // Convert Role object → String
        dto.setRole(user.getRole().getRoleName().name());

        dto.setIsActive(user.getIsActive());

        return dto;
    }

    // 🔷 Convert User Entity → AuthResponseDTO
    public static AuthResponseDTO toAuthResponse(User user, String token) {

        return new AuthResponseDTO(
                token,
                user.getUsername(),
                user.getRole().getRoleName().name()
        );
    }
}
