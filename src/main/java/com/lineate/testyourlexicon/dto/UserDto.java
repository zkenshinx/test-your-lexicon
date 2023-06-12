package com.lineate.testyourlexicon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class UserDto {

  private final int code = HttpStatus.CREATED.value();
  private String firstName;
  private String lastName;
  private String email;


}
