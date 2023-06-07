package com.springboot.rest.api.blog.kafka.dto;

import com.springboot.rest.api.blog.controller.dto.NewRemoteCommentDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NewRemoteCommentAsyncDto {

    private Long postId;
    private NewRemoteCommentDto newRemoteCommentDto;

}
