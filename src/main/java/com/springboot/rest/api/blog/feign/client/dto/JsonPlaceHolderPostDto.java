package com.springboot.rest.api.blog.feign.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class JsonPlaceHolderPostDto {
    private String title;
    @JsonProperty("body")
    private String content;
    private LocalDateTime creationDate = LocalDateTime.now();
}
