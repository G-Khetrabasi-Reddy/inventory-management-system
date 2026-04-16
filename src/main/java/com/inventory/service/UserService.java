package com.inventory.service;

import com.inventory.dto.UserDTO;

import java.util.List;

public interface UserService {

    List<UserDTO> getAllUsers();

    UserDTO getUserById(Long id);

    void deleteUser(Long id);

    UserDTO deactivateUser(Long id);

    UserDTO activateUser(Long id);

    UserDTO assignRole(Long userId, String roleName);
}
