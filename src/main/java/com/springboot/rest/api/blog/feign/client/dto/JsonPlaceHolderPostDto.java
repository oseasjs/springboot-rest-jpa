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
public class JsonPlaceHolderPostDto {
    private String title;

    @JsonProperty("body")
    private String content;

    @JsonIgnore
    @Getter
    private final GeneratedTypeEnum generatedType = GeneratedTypeEnum.AUTO;

    @JsonIgnore
    @Getter
    private LocalDateTime creationDate = LocalDateTime.now();
}
