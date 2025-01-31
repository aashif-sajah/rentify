package com.rentify.service;

import com.rentify.model.JwtRequest;
import com.rentify.model.JwtResponse;
import com.rentify.model.Users;
import com.rentify.repository.UserRepo;
import com.rentify.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

  private final JwtUtil jwtUtil;

  private final AuthenticationManager authenticationManager;

  private final UserDetailsService myUserDetailsService;

  private final UserRepo userRepo;

  public JwtService(
      JwtUtil jwtUtil,
      AuthenticationManager authenticationManager,
      UserDetailsService myUserDetailsService,
      UserRepo userRepo) {
    this.jwtUtil = jwtUtil;
    this.authenticationManager = authenticationManager;
    this.myUserDetailsService = myUserDetailsService;
    this.userRepo = userRepo;
  }

  public JwtResponse createJwtToken(JwtRequest jwtRequest) throws Exception {
    // String userName = jwtRequest.getUserEmail();

    String userEmail = jwtRequest.getUserEmail();
    String userPassword = jwtRequest.getUserPassword();
    authenticate(userEmail, userPassword);

    final UserDetails userDetails = myUserDetailsService.loadUserByUsername(userEmail);
    String newGeneratedJwtToken = jwtUtil.generateToken(userDetails);
    Users user =
        //userRepo.findById(userName).orElseThrow(() -> new RuntimeException("User not found"));
          userRepo.findByUserEmail(userEmail).orElseThrow(() -> new RuntimeException("User not found"));

    return new JwtResponse(user, newGeneratedJwtToken);
  }

  private void authenticate(String userEmail, String password) throws Exception {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(userEmail, password));
    } catch (DisabledException e) {
      throw new Exception("User is Disabled");
    } catch (BadCredentialsException e) {
      throw new Exception("Invalid username or password");
    }
  }
}
