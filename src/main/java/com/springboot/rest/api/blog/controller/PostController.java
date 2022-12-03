package com.springboot.rest.api.blog.controller;

import com.springboot.rest.api.blog.controller.dto.NewPostDto;
import com.springboot.rest.api.blog.controller.dto.PostDto;
import com.springboot.rest.api.blog.controller.mapper.PostMapper;
import com.springboot.rest.api.blog.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/v1/posts")
@Tag(name = "Post API for the Blog")
@AllArgsConstructor
public class PostController {
    private PostService postService;

    @Operation(summary = "Get Post by ID", responses = {
        @ApiResponse(responseCode = "200",
            description = "Ok",
            content = @Content(mediaType = "application/json",schema = @Schema(implementation = PostDto.class))
        ),
        @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PostDto getPost(@PathVariable Long id) {
        return Optional
            .of(this.postService.getPost(id))
            .map(PostMapper.INSTANCE::toDTO)
            .get();
    }

    @Operation(summary = "Add new Post", responses = {
        @ApiResponse(responseCode = "201",
            description = "Ok",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Long.class))),
        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long addPost(@Valid @RequestBody NewPostDto newPostDto) {
        return this.postService.addPost(PostMapper.INSTANCE.toEntity(newPostDto));
    }

}
