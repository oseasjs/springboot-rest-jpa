package com.springboot.rest.api.blog.controller;

import com.springboot.rest.api.blog.controller.dto.NewPostDto;
import com.springboot.rest.api.blog.controller.dto.PostDto;
import com.springboot.rest.api.blog.controller.mapper.PostMapper;
import com.springboot.rest.api.blog.service.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/v1/posts")
@Api(value = "Post API for the Blog")
@AllArgsConstructor
public class PostController {
    private PostService postService;
    @ApiOperation(value = "Get Post by ID", produces = "application/json")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Ok"),
        @ApiResponse(code = 400, message = "Bad Request"),
        @ApiResponse(code = 404, message = "Not Found")
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PostDto getPost(@PathVariable Long id) {
        return Optional
            .of(this.postService.getPost(id))
            .map(PostMapper.INSTANCE::toDTO)
            .get();
    }

    @ApiOperation(value = "Add new Post", produces = "application/json")
    @ApiResponses({
        @ApiResponse(code = 201, message = "Created"),
        @ApiResponse(code = 400, message = "Bad Request")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long addPost(@Valid @RequestBody NewPostDto newPostDto) {
        return this.postService.addPost(PostMapper.INSTANCE.toEntity(newPostDto));
    }

}
