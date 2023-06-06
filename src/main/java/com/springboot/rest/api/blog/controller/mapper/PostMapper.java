package com.springboot.rest.api.blog.controller.mapper;

import com.springboot.rest.api.blog.controller.dto.NewPostDto;
import com.springboot.rest.api.blog.controller.dto.PostDto;
import com.springboot.rest.api.blog.feign.client.dto.JsonPlaceHolderPostDto;
import com.springboot.rest.api.blog.model.Post;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PostMapper {

  PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

  Post toEntity(NewPostDto source);

  Post toEntity(PostDto source);

  Post toEntity(JsonPlaceHolderPostDto source);

  PostDto toDTO(Post destination);

  List<PostDto> asDtoList(List<Post> commentList);

}
