package com.inventory.security;

import com.inventory.entity.User;
import com.inventory.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    // 🔷 Load user from DB
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Fetch user from database
        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with username: " + username)
                );

        // 🔥 Convert Role → Spring Security format
        String role = "ROLE_" + user.getRole().getRoleName().name();

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPasswordHash(),
                Collections.singletonList(new SimpleGrantedAuthority(role))
        );
    }
}