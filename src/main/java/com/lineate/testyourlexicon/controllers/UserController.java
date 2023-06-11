package com.lineate.testyourlexicon.controllers;

import com.lineate.testyourlexicon.dto.UserDto;
import com.lineate.testyourlexicon.dto.UserRegistrationDto;
import com.lineate.testyourlexicon.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import net.sf.saxon.type.ValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping("/register")
  public ResponseEntity<?> registerUser(@RequestBody @Valid UserRegistrationDto userRegistrationDto) throws ValidationException {
    return userService.createUser(userRegistrationDto);
  }

  @GetMapping("/all")
  public List<UserDto> allUsers() {
    return userService.getAll();
  }

}
