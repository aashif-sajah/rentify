package com.rentify.service;

import com.rentify.model.Users;
import com.rentify.repository.UserRepo;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class MyUserDetailsService implements UserDetailsService {

  private final UserRepo userRepo;

  public MyUserDetailsService(UserRepo userRepo) {
    this.userRepo = userRepo;
  }

  @Override
  public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
    // Users user = userRepo.findById(username).get();

    Users user =
        userRepo
            .findByUserEmail(userEmail)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    if (user != null) {
      return new User(user.getUsername(), user.getUserPassword(), getAuthorities(user));

    } else {
      throw new UsernameNotFoundException("User not found");
    }
  }

  private Set<SimpleGrantedAuthority> getAuthorities(Users user) {
    Set<SimpleGrantedAuthority> authorities = new HashSet<>();

    user.getRoles()
        .forEach(
            role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRole())));
    return authorities;
  }
}
