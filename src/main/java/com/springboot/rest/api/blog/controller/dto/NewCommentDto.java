package com.springboot.rest.api.blog.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.springboot.rest.api.blog.enums.GeneratedTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
@Getter
public class NewCommentDto {
  @JsonIgnore
  @Getter
  private final GeneratedTypeEnum generatedType = GeneratedTypeEnum.MANUAL;
  @NotBlank(message = "Content is required")
  private String content;
  @NotBlank(message = "Author is required")
  private String author;
  @NotNull(message = "Creation Date is required")
  private LocalDateTime creationDate;
  @JsonIgnore
  @Setter
  private Long postId;
}
