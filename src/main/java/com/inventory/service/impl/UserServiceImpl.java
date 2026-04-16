package com.inventory.service.impl;

import com.inventory.dto.UserDTO;
import com.inventory.entity.Role;
import com.inventory.entity.User;
import com.inventory.enums.RoleType;
import com.inventory.exceptions.UserNotFoundException;
import com.inventory.mapper.UserMapper;
import com.inventory.repository.RoleRepository;
import com.inventory.repository.UserRepository;
import com.inventory.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    // 🔷 GET ALL USERS
    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    // 🔷 GET USER BY ID
    @Override
    @Transactional(readOnly = true)
    public UserDTO getUserById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        return UserMapper.toDTO(user);
    }

    // 🔷 DELETE USER
    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // 🔷 DEACTIVATE USER
    @Override
    @Transactional
    public UserDTO deactivateUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        user.setIsActive(false);

        return UserMapper.toDTO(userRepository.save(user));
    }

    // 🔷 ACTIVATE USER
    @Override
    @Transactional
    public UserDTO activateUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        user.setIsActive(true);

        return UserMapper.toDTO(userRepository.save(user));
    }

    // 🔷 ASSIGN ROLE
    @Override
    @Transactional
    public UserDTO assignRole(Long userId, String roleName) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        RoleType roleType = RoleType.valueOf(roleName);

        Role role = roleRepository.findByRoleName(roleType)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        user.setRole(role);

        return UserMapper.toDTO(userRepository.save(user));
    }
}