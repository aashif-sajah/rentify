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
  private final RoleService roleService;

  public UserService(UserRepo userRepo, PasswordEncoder bCryptPasswordEncoder, RoleRepo roleRepo, RoleService roleService) {
    this.userRepo = userRepo;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    this.roleRepo = roleRepo;
    this.roleService = roleService;
  }

  public Users registerNewUser(Users users) {
    Role role;
    if (users.getRoles() == null || users.getRoles().isEmpty()) {
      Set<Role> roles = new HashSet<>();
      role = roleRepo.findById("Owner").orElseThrow(() -> new RuntimeException("Role not found"));
      roles.add(role);
      users.setRoles(roles);
    } else {
      role = roleRepo.findById("Customer").orElseThrow(() -> new RuntimeException("Role not found"));
      users.getRoles().clear();
      users.getRoles().add(role);
      Users user = userRepo.findByUserEmail(users.getUserEmail()).orElse(null);
      if (user != null) { return user; }
    }

    users.setUserPassword(getEncodedPassword(users.getUserPassword()));
    return userRepo.save(users);
  }

  public String getEncodedPassword(String password) {
    return bCryptPasswordEncoder.encode(password);
  }

  public Users updateUser(Long id, Users request) {
    Users user = userRepo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

    if (request.getUserEmail() != null) {
      user.setUserEmail(request.getUserEmail());
    }
    if (request.getUserPassword() != null) {
      user.setUserPassword(getEncodedPassword(request.getUserPassword()));
    }
    if (request.getUserFirstName() != null) {
      user.setUserFirstName(request.getUserFirstName());
    }
    if (request.getUserLastName() != null) {
      user.setUserLastName(request.getUserLastName());
    }
    if (request.getUsername() != null) {
      user.setUsername(request.getUsername());
    }
    return userRepo.save(user);
  }
}
