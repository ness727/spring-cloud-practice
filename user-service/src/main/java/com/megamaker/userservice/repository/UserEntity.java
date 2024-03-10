package com.megamaker.userservice.repository;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String name;

    @Column(name = "user_id")
    private String userId;

    private String pwd;
}
