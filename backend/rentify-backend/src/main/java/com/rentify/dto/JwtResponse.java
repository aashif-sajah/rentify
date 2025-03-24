package com.rentify.dto;

import com.rentify.model.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class JwtResponse
{
    private Users user;
    private String jwtToken;
    private boolean isBusinessAvailable;
    private BusinessResponse businessResponse;

}
