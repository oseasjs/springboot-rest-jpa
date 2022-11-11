package com.springboot.rest.api.blog.controller;

import com.springboot.rest.api.blog.dto.PostDto;
import com.springboot.rest.api.blog.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
public class PostController {
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PostDto getPost(@PathVariable Long id) {
        return this.postService.getPost(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long addPost(@RequestBody PostDto postDto) {
        return this.postService.addPost(postDto);
    }

}
