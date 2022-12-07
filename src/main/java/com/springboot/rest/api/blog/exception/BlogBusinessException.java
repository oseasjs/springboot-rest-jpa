package com.springboot.rest.api.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BlogBusinessException extends RuntimeException {

  public BlogBusinessException(String message) {
    super(message);
  }

}
