package com.rentify.service;

import com.rentify.model.Role;
import com.rentify.model.Users;
import com.rentify.repository.RoleRepo;
import com.rentify.repository.UserRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {
  private final UserRepo userRepo;
  private final PasswordEncoder bCryptPasswordEncoder;
  private final RoleRepo roleRepo;

  public UserService(UserRepo userRepo, PasswordEncoder bCryptPasswordEncoder, RoleRepo roleRepo) {
    this.userRepo = userRepo;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    this.roleRepo = roleRepo;
  }

  public Users registerNewUser(Users users) {
    Role role = roleRepo.findById("Owner").orElseThrow(() -> new RuntimeException("Role not found"));

    if (users.getRoles() == null) {
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        users.setRoles(roles);
    }

    users.setUserPassword(getEncodedPassword(users.getUserPassword()));
    return userRepo.save(users);
  }

  public String getEncodedPassword(String password) {
    return bCryptPasswordEncoder.encode(password);
  }
}
