package com.springboot.rest.api.blog.controller.mapper;

import com.springboot.rest.api.blog.controller.dto.CommentDto;
import com.springboot.rest.api.blog.controller.dto.NewCommentDto;
import com.springboot.rest.api.blog.model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CommentMapper {

    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    @Mapping(source="postId", target="post.id")
    Comment toEntity(NewCommentDto source);

    @Mapping(source="post.id", target="postId")
    CommentDto toDTO(Comment source);

    @Mapping(source="post.id", target="postId")
    List<CommentDto> asDtoList(List<Comment> commentList);

}
