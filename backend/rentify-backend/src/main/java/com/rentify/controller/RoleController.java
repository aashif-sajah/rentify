package com.rentify.controller;


import com.rentify.model.Role;
import com.rentify.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/auth")
public class RoleController
{
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/git")
    public Role createNewRole(@RequestBody Role role)
    {
        return roleService.createRole(role);
    }
}
