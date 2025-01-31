package com.rentify.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JwtResponse
{
    private Users user;
    private String jwtToken;

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public JwtResponse(Users user, String jwtToken) {
        this.user = user;
        this.jwtToken = jwtToken;
    }

}
