package com.rentify.controller;


import com.rentify.model.Role;
import com.rentify.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class RoleController
{
    @Autowired
    private RoleService roleService;

    @PostMapping("/createNewRole")
    public Role createNewRole(@RequestBody Role role)
    {
        return roleService.createRole(role);
    }
}
