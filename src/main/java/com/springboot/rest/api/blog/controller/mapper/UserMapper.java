package com.springboot.rest.api.blog.controller.mapper;

import com.springboot.rest.api.blog.controller.dto.NewUserDto;
import com.springboot.rest.api.blog.controller.dto.UserDto;
import com.springboot.rest.api.blog.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

  UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

  User toEntity(NewUserDto newUserDto);
  UserDto toDto(User User);

}
