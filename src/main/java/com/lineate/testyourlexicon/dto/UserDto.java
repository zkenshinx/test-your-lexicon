package com.lineate.testyourlexicon.dto;

public record UserDto(String firstName, String lastName, String email) {
  public UserDto(String firstName, String lastName, String email) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
  }

}
