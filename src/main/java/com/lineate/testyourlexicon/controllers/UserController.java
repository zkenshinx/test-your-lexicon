package com.lineate.testyourlexicon.controllers;

import com.lineate.testyourlexicon.dto.UserDto;
import com.lineate.testyourlexicon.dto.UserRegistrationDto;
import com.lineate.testyourlexicon.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping
  public ResponseEntity<UserDto> registerUser(@RequestBody @Valid UserRegistrationDto userRegistrationDto) {
    return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userRegistrationDto));
  }

  @GetMapping
  public List<UserDto> allUsers() {
    return userService.getAll();
  }

}
