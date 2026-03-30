package com.inventory.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity //This tells Jakarta Persistence that this class represents a table in DB.
@Table(name = "users") //By default, JPA would name the table User.
@Data //automatically generates all the boilerplate code you normally have to write: Getters, Setters, toString(), equals(), and hashCode() methods.
@NoArgsConstructor
@AllArgsConstructor
//More Lombok magic. These automatically generate two constructors for the class:
// one with no arguments (required by JPA) and
// one with an argument for every single field.
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false, unique = true)
    private String email;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false) //This creates the Foreign Key column in the users table.
    private Role role;

    private Boolean isActive;

    private LocalDateTime createAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastLogin;

}
