package com.rentify.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class JwtRequest {
  private String userEmail;
  private String userPassword;
}
