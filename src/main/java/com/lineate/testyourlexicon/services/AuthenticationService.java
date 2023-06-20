package com.lineate.testyourlexicon.services;

import com.lineate.testyourlexicon.entities.User;
import com.lineate.testyourlexicon.exceptions.UserNotAuthenticatedException;
import com.lineate.testyourlexicon.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

  private final UserRepository userRepository;
  public boolean isAuthenticated() {
    Authentication auth =
      SecurityContextHolder.getContext().getAuthentication();
    return !(auth instanceof AnonymousAuthenticationToken);
  }

  public String getAuthenticatedUserEmail() {
    return SecurityContextHolder.getContext().getAuthentication().getName();
  }

  public Optional<User> getAuthenticatedUser() {
    String email = this.getAuthenticatedUserEmail();
    return userRepository.findUserByEmail(email);
  }
}
