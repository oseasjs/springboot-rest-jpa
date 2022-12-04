package com.springboot.rest.api.blog.controller.dto;

import com.springboot.rest.api.blog.enums.GeneratedTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@Getter
public class PostDto {
  private Long id;
  private String title;
  private String content;
  private LocalDateTime creationDate;
  private GeneratedTypeEnum generatedType;
}
