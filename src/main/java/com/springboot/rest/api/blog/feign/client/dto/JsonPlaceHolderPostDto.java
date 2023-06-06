package com.springboot.rest.api.blog.feign.client.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.springboot.rest.api.blog.enums.GeneratedTypeEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JsonPlaceHolderPostDto {
  @JsonIgnore
  @Getter
  private final GeneratedTypeEnum generatedType = GeneratedTypeEnum.AUTO;
  private String title;
  @JsonProperty("body")
  private String content;
  @JsonIgnore
  @Getter
  private LocalDateTime creationDate = LocalDateTime.now();
}
