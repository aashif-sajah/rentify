package com.rentify.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JwtResponse
{
    private Users user;
    private String jwtToken;

    public JwtResponse(Users user, String jwtToken) {
        this.user = user;
        this.jwtToken = jwtToken;
    }

}
