package com.springboot.rest.api.blog.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthenticationDto {

  @NotBlank(message = "Username is required")
  private String username;
  @NotBlank(message = "Password is required")
  private String password;

}
