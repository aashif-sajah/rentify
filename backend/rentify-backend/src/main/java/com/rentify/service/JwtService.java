package com.rentify.service;

import com.rentify.exception.InvalidCredentialsException;
import com.rentify.exception.UserDisabledException;
import com.rentify.model.JwtRequest;
import com.rentify.model.JwtResponse;
import com.rentify.model.Users;
import com.rentify.repository.BusinessRepo;
import com.rentify.repository.UserRepo;
import com.rentify.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JwtService {

  private final JwtUtil jwtUtil;
  private final AuthenticationManager authenticationManager;
  private final UserDetailsService myUserDetailsService;
  private final UserRepo userRepo;
  private final BusinessRepo businessRepo;

  public JwtResponse createJwtToken(JwtRequest jwtRequest) {

    String userEmail = jwtRequest.getUserEmail();
    String userPassword = jwtRequest.getUserPassword();
    authenticate(userEmail, userPassword);

    final UserDetails userDetails = myUserDetailsService.loadUserByUsername(userEmail);
    String newGeneratedJwtToken = jwtUtil.generateToken(userDetails);
    Users user =
        userRepo
            .findByUserEmail(userEmail)
            .orElseThrow(() -> new RuntimeException("User not found"));

    boolean isBusinessAvailable = businessRepo.findByOwner(user).isPresent();
    return new JwtResponse(user, newGeneratedJwtToken, isBusinessAvailable);
  }

  private void authenticate(String userEmail, String password){
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(userEmail, password));
    } catch (DisabledException e) {
      throw new UserDisabledException("User is disabled. Please contact support.");
    } catch (BadCredentialsException e) {
      throw new InvalidCredentialsException("Invalid username or password.");
    }
  }
}
