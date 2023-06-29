package com.springboot.rest.api.blog.kafka.dto;

import com.springboot.rest.api.blog.controller.dto.NewRemotePostDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NewRemotePostAsyncDto {

    private NewRemotePostDto postDto;

}
