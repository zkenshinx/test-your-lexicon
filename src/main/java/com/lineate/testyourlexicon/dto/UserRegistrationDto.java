package com.lineate.testyourlexicon.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class UserRegistrationDto {

  @NotEmpty
  @JsonProperty("first_name")
  @Pattern(regexp = "[A-Za-z]+", message = "First name must be only english characters")
  private String firstName;

  @NotEmpty
  @JsonProperty("last_name")
  @Pattern(regexp = "[A-Za-z]+", message = "Last name must be only english characters")
  private String lastName;

  @NotEmpty
  @Email
  private String email;

  @NotEmpty
  @Length(min = 8, message = "Password length must be minimum 8")
  @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]+$", message = "Password must contain both letters and numbers")
  private String password;
  @NotEmpty
  private String confirmationPassword;
}
