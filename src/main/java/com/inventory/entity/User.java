package com.inventory.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity //This tells Jakarta Persistence that this class represents a table in DB.
@Table(name = "users") //By default, JPA would name the table User.
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//More Lombok magic. These automatically generate two constructors for the class:
// one with no arguments (required by JPA) and
// one with an argument for every single field.
public class User extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_gen")
    @SequenceGenerator(name = "user_gen", sequenceName = "user_seq", allocationSize = 1)
    private Long userId;

    @Column(nullable = false, unique = true)
    private String username;

    @ToString.Exclude
    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false, unique = true)
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false) //This creates the Foreign Key column in the users table.
    private Role role;

    private Boolean isActive = true;

    private LocalDateTime lastLogin;
}
