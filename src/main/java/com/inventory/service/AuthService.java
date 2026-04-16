package com.inventory.service;

import com.inventory.dto.RegisterDTO;
import com.inventory.dto.LoginRequestDTO;
import com.inventory.dto.AuthResponseDTO;

public interface AuthService {

    AuthResponseDTO register(RegisterDTO dto);

    AuthResponseDTO login(LoginRequestDTO dto);

    void validateUser(String username, String password); // internal use
}
