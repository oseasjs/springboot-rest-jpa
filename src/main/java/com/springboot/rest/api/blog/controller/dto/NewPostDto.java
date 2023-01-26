package com.springboot.rest.api.blog.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.springboot.rest.api.blog.enums.GeneratedTypeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@Getter
public class NewPostDto {
  @JsonIgnore
//  @Getter
  private final GeneratedTypeEnum generatedType = GeneratedTypeEnum.MANUAL;

  @NotBlank(message = "Title is required")
  private String title;

  @NotBlank(message = "Content is required")
  private String content;

  @NotNull(message = "Creation Date is required")
  private LocalDateTime creationDate;
}
