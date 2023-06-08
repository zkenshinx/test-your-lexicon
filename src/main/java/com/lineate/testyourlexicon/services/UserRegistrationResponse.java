package com.lineate.testyourlexicon.services;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class UserRegistrationResponse {

  private boolean successfulRegistration;
  private String message;

}
