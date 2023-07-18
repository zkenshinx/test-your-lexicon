package com.lineate.testyourlexicon.controllers;

import com.lineate.testyourlexicon.dto.AuthenticationDto;
import com.lineate.testyourlexicon.dto.LoggedInDto;
import com.lineate.testyourlexicon.dto.SingleMessageDto;
import com.lineate.testyourlexicon.services.AuthenticationService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthenticationController {

  private final SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
  private final AuthenticationService authenticationService;

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
  public SingleMessageDto logout(Authentication authentication,
                                 HttpServletRequest request,
                                 HttpServletResponse response) {
    logoutHandler.logout(request, response, authentication);
    return new SingleMessageDto("Logout successful");
  }

  @GetMapping("/authentication")
  public LoggedInDto isAuthenticated() {
    return new LoggedInDto(authenticationService.isAuthenticated());
  }
}
