package com.lineate.testyourlexicon.controllers;

import com.lineate.testyourlexicon.dto.AuthenticationDto;
import com.lineate.testyourlexicon.dto.SingleMessageDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLOutput;


@RestController
public class AuthenticationController {

  @PostMapping("/login")
  @ResponseStatus(HttpStatus.OK)
  public SingleMessageDto login(@RequestBody @Valid AuthenticationDto authenticationDto,
                              HttpServletRequest request) {
    try {
      request.login(authenticationDto.getEmail(), authenticationDto.getPassword());
    } catch (ServletException e) {
      throw new IllegalArgumentException("Login unsuccessful");
    }
    return new SingleMessageDto("Login successful");
  }

  @GetMapping("/logout")
  @ResponseStatus(HttpStatus.OK)
  public SingleMessageDto logout(HttpServletRequest request) {
    try {
      request.logout();
    } catch (ServletException e) {
      throw new IllegalArgumentException("Logout unsuccessful");
    }
    return new SingleMessageDto("Logout successful");
  }

}
