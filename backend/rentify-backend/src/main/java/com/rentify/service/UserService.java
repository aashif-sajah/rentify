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
    Role role =
        roleRepo.findById("Owner").orElseThrow(() -> new RuntimeException("Role not found"));
    System.out.println(users.getRoles() == null || users.getRoles().isEmpty());
    System.out.println("Before :" + users.getRoles());
    if (users.getRoles() == null || users.getRoles().isEmpty()) {
      Set<Role> roles = new HashSet<>();
      roles.add(role);
      users.setRoles(roles);
    }
    System.out.println("After :" + users.getRoles());

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
