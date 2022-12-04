package com.springboot.rest.api.blog.feign.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.springboot.rest.api.blog.enums.GeneratedTypeEnum;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JsonPlaceHolderCommentDto {

  @JsonIgnore
  @Getter
  private final GeneratedTypeEnum generatedType = GeneratedTypeEnum.AUTO;
  private Long postId;
  @JsonProperty("body")
  private String content;
  @JsonProperty("email")
  private String author;
  @JsonIgnore
  @Getter
  private LocalDateTime creationDate = LocalDateTime.now();

}
