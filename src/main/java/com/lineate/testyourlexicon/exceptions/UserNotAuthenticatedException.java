package com.lineate.testyourlexicon.exceptions;

public class UserNotAuthenticatedException extends RuntimeException {
  public UserNotAuthenticatedException(String userNotAuthenticated) {
    super(userNotAuthenticated);
  }
}
