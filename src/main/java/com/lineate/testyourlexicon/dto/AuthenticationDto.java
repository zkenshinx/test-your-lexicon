package com.lineate.testyourlexicon.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AuthenticationDto {

  @NotEmpty(message = "Email must not be empty")
  @JsonProperty("email")
  private String email;
  @NotEmpty(message = "Password must not be empty")
  @JsonProperty("password")
  private String password;

}
