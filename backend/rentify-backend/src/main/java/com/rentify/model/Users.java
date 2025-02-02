package com.rentify.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
public class Users {

    @Column(unique = true, nullable = false)
    @Id
    private String username;

    @Column(unique = true, nullable = false)
    private String userEmail;

    @Column(nullable = false)
    private String userPassword;

    private String userFirstName;
    private String userLastName;
    private String businessType;
    private String profileImage;



    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "USER_ROLE",
            joinColumns = {
                    @JoinColumn(name = "USER_ID")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "ROLE_ID")
            }
    )
    private Set<Role> roles;
}
