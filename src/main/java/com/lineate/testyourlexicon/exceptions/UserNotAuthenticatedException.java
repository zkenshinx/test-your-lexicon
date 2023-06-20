package com.lineate.testyourlexicon.exceptions;

public class UserNotAuthenticatedException extends RuntimeException {
  private static final String USER_NOT_AUTHENTICATED_MESSAGE = "User not authenticated";

  public UserNotAuthenticatedException() {
    super(USER_NOT_AUTHENTICATED_MESSAGE);
  }
}
