package com.lineate.testyourlexicon.services;

import com.lineate.testyourlexicon.dto.UserRegistrationDto;

public interface UserService {

  public UserRegistrationResponse createUser(UserRegistrationDto userRegistrationDto);

}
