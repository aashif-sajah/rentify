package com.rentify.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Role
{
    @Id
    String role;
    String roleDescription;

}
