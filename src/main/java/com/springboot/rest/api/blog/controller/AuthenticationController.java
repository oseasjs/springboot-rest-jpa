package com.springboot.rest.api.blog.controller;

import com.springboot.rest.api.blog.controller.dto.AuthenticationDto;
import com.springboot.rest.api.blog.controller.dto.NewUserDto;
import com.springboot.rest.api.blog.controller.dto.UserDto;
import com.springboot.rest.api.blog.controller.mapper.UserMapper;
import com.springboot.rest.api.blog.exception.BlogBusinessException;
import com.springboot.rest.api.blog.security.JwtUtils;
import com.springboot.rest.api.blog.service.UserDetailsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/auth")
@Tag(name = "Authentication API")
@RequiredArgsConstructor
public class AuthenticationController {

  private final UserDetailsService userService;
  private final JwtUtils jwtUtils;
  private final AuthenticationManager authenticationManager;

  @Operation(summary = "Register new users", responses = {
    @ApiResponse(responseCode = "201",
      description = "Ok",
      content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class))
    ),
    @ApiResponse(responseCode = "400", description = "Bad Request")
  })
  @PostMapping("/register")
  @ResponseStatus(HttpStatus.CREATED)
  public UserDto register(@RequestBody final NewUserDto dto) {
    return
      Optional
        .of(userService.save(UserMapper.INSTANCE.toEntity(dto)))
        .map(UserMapper.INSTANCE::toDto)
        .get();
  }

  @PostMapping("/authenticate")
  public String authenticate(@RequestBody final AuthenticationDto dto) {
    authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(dto.getUsername(),dto.getPassword())
    );

    final UserDetails user = userService.loadUserByUsername(dto.getUsername());
    if (user != null) {
      return jwtUtils.generateToken(user);
    }
    else {
      throw new BlogBusinessException("Login failed");
    }
  }

}
