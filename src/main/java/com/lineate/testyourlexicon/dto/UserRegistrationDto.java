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
  @Pattern(regexp = "[A-Za-z]+", message = "First name must be only english characters")
  @JsonProperty("first_name")
  private String firstName;

  @NotEmpty
  @Pattern(regexp = "[A-Za-z]+", message = "Last name must be only english characters")
  @JsonProperty("last_name")
  private String lastName;

  @NotEmpty
  @Email(message = "Email not valid")
  private String email;

  @NotEmpty
  @Length(min = 8, message = "Password length must be minimum 8")
  @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]+$", message = "Password must contain both letters and numbers")
  private String password;
  @NotEmpty
  @JsonProperty("confirmation_password")
  private String confirmationPassword;
}
