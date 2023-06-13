package com.lineate.testyourlexicon.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class ExceptionResponse {

  private final int code = HttpStatus.BAD_REQUEST.value();
  private final Object message;

}
