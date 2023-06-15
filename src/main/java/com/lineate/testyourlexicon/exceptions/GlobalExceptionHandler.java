package com.lineate.testyourlexicon.exceptions;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

  // Handles validation errors and returns error messages
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex) {
    BindingResult bindingResult = ex.getBindingResult();
    List<FieldError> fieldErrors = bindingResult.getFieldErrors();
    String error = fieldErrors.stream()
      .map(f -> f.getDefaultMessage()).collect(Collectors.toList())
      .get(0);
    return ResponseEntity.badRequest().body(new ExceptionResponse(error));
  }

  @ExceptionHandler({IllegalArgumentException.class, UserNotAuthenticatedException.class})
  public ResponseEntity<?> handleIllegalArgumentException(Exception ex) {
    return ResponseEntity.badRequest().body(new ExceptionResponse(ex.getMessage()));
  }

}
