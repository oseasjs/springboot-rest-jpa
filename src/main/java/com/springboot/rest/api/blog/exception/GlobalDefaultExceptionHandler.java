package com.springboot.rest.api.blog.exception;

import com.springboot.rest.api.blog.controller.dto.ErrorResponseDto;
import com.springboot.rest.api.blog.util.JsonUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@AllArgsConstructor
@Slf4j
class GlobalDefaultExceptionHandler {

  private JsonUtil jsonUtil;

  @ExceptionHandler(value = Exception.class)
  public ResponseEntity defaultErrorHandler(Exception e) {
    log.error(e.getMessage(), e);
    return responseEntity(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
  }

  @ExceptionHandler({NotFoundException.class})
  public ResponseEntity handlerNotFoundExceptions(Exception e) {
    log.error(e.getMessage(), e);
    return responseEntity(HttpStatus.NOT_FOUND, e.getMessage());
  }

  @ExceptionHandler({BlogBusinessException.class})
  public ResponseEntity handlerBlogBusinessExceptions(Exception e) {
    log.error(e.getMessage(), e);
    return responseEntity(HttpStatus.BAD_REQUEST, e.getMessage());
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity handleValidationExceptions(MethodArgumentNotValidException e) {
    log.error(e.getMessage(), e);

    Map<String, String> errors = new HashMap<>();
    e.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });
    return responseEntity(HttpStatus.BAD_REQUEST, errors);
  }

  private ResponseEntity responseEntity(HttpStatus httpStatus, String errorMessage) {
    return ResponseEntity
      .status(httpStatus)
      .body(ErrorResponseDto
        .builder()
        .title("Error")
        .message(errorMessage)
        .status(httpStatus.value())
        .timestamp(LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond())
        .build());
  }

  private ResponseEntity responseEntity(HttpStatus httpStatus, Map<String, String> errors) {
    return ResponseEntity
      .status(httpStatus)
      .body(ErrorResponseDto
        .builder()
        .title("Error")
        .message("Validation Error")
        .validationMessage(errors)
        .status(httpStatus.value())
        .timestamp(LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond())
        .build());
  }

}