package com.aiox.productapi.api;

import java.time.Instant;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {
  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  ErrorResponse illegalArgument(IllegalArgumentException ex) {
    return new ErrorResponse(Instant.now(), ex.getMessage());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  ErrorResponse validation(MethodArgumentNotValidException ex) {
    return new ErrorResponse(Instant.now(), "invalid request");
  }

  public record ErrorResponse(Instant timestamp, String message) {}
}
