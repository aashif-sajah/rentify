package com.rentify.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String userEmail;

    @Column(nullable = false)
    private String userPassword;

    private String userFirstName;
    private String userLastName;



    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "USER_ROLE",
            joinColumns = {
                    @JoinColumn(
                            name = "USER_ID",
                            referencedColumnName = "userId"
                    )
            },
            inverseJoinColumns = {
                    @JoinColumn(
                            name = "ROLE_ID",
                            referencedColumnName = "role")
            }
    )
    private Set<Role> roles;
}
