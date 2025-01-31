package com.rentify.service;

import com.rentify.model.Role;
import com.rentify.repository.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RoleService
{
    @Autowired
    private RoleRepo roleRepo;


    public Role createRole(Role role)
    {
        return roleRepo.save(role);
    }
}
