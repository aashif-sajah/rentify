package com.rentify.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Role
{
    @Id
    String role;
    String roleDescription;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }
}
