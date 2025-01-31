package com.rentify.controller;

import com.rentify.model.JwtRequest;
import com.rentify.model.JwtResponse;
import com.rentify.service.JwtService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/auth")
public class JwtController
{
    private final JwtService jwtService;

    public JwtController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("/authenticate")
        public JwtResponse createJwtToken(@RequestBody JwtRequest jwtRequest) throws Exception {
            return jwtService.createJwtToken(jwtRequest);
        }
}
