package com.springboot.rest.api.blog.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class NewUserDto {

  @NotBlank(message = "Username is required")
  private String username;

  @NotBlank(message = "Password is required")
  private String password;

}
