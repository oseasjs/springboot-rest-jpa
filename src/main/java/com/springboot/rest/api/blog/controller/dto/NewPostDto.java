package com.springboot.rest.api.blog.controller.dto;

import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@Getter
public class NewPostDto {
    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Content is required")
    private String content;

    @NotNull(message = "Creation Date is required")
    private LocalDateTime creationDate;
}
