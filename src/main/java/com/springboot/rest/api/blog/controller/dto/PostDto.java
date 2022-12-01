package com.springboot.rest.api.blog.controller.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@Getter
public class PostDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime creationDate;
}
