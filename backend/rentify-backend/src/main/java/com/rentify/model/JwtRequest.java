package com.rentify.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JwtRequest
{
    private String userEmail;
    private String userPassword;

}
