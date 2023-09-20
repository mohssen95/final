package org.neshan.apireportservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.neshan.apireportservice.entity.model.enums.Role;

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


}
