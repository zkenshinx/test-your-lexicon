package com.lineate.testyourlexicon.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserInterfaceController {

  @GetMapping("/login")
  public String login() {
    return "login.html";
  }

}
