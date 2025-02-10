package com.rentify.controller;

import com.rentify.model.JwtRequest;
import com.rentify.model.JwtResponse;
import com.rentify.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/auth")
public class JwtController {
  private final JwtService jwtService;

  public JwtController(JwtService jwtService) {
    this.jwtService = jwtService;
  }

  //        public JwtResponse createJwtToken(@RequestBody JwtRequest jwtRequest) throws Exception {
  //            return jwtService.createJwtToken(jwtRequest);
  //        }
  @PostMapping("/authenticate")
  public ResponseEntity<JwtResponse> createJwtToken(@RequestBody JwtRequest jwtRequest) {
    System.out.println(jwtRequest.toString());
    JwtResponse response = jwtService.createJwtToken(jwtRequest);
    return ResponseEntity.ok(response);
  }
}
