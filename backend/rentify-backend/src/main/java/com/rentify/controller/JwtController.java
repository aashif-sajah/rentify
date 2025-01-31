package com.rentify.controller;

import com.rentify.model.JwtRequest;
import com.rentify.model.JwtResponse;
import com.rentify.service.JwtService;
import com.rentify.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class JwtController
{
    @Autowired
    private JwtService jwtService;

        @PostMapping("/authenticate")
        public JwtResponse createJwtToken(@RequestBody JwtRequest jwtRequest) throws Exception {
            return jwtService.createJwtToken(jwtRequest);
        }
}
