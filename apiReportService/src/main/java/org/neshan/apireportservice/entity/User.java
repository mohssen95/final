package org.neshan.apireportservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.neshan.apireportservice.entity.model.enums.Role;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user_tb")
@RequiredArgsConstructor
@Setter
@Getter

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "trust")
    private float trust;

    @Column(name = "role")
    private Role role;

    @OneToMany(mappedBy = "user")
    private Set<Interaction> interactions=new HashSet<>();


}
