package com.springboot.rest.api.blog.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
@Getter
public class CommentDto {
    private Long id;
    private Long postId;
    private String content;
    private String author;

    private LocalDateTime creationDate;
}
