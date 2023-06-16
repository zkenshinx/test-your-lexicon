package com.lineate.testyourlexicon.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AuthenticationDto {

  @NotEmpty(message = "Email must not be empty")
  private String email;
  @NotEmpty(message = "Password must not be empty")
  private String password;

}
