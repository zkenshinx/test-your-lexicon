package com.lineate.testyourlexicon.security;

import com.lineate.testyourlexicon.entities.User;
import com.lineate.testyourlexicon.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class SecurityUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userRepository.findUserByEmail(email)
        .orElseThrow(() ->
        new UsernameNotFoundException(String.format("User with email: %s not found", email)));
    return new SecurityUser(user);
  }
}
