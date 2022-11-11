package com.springboot.rest.api.blog.controller;

import com.springboot.rest.api.blog.dto.CommentDto;
import com.springboot.rest.api.blog.dto.NewCommentDto;
import com.springboot.rest.api.blog.service.CommentService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@RestController
@RequestMapping("/posts/{postId}/comments")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CommentDto> getCommentsForPost(@PathVariable Long postId) {
        return this.commentService.getCommentsForPost(postId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long addComment(@PathVariable Long postId, @RequestBody NewCommentDto newCommentDto) {
        newCommentDto.setPostId(postId);
        return this.commentService.addComment(newCommentDto);
    }

    @ExceptionHandler({ IllegalArgumentException.class })
    public ResponseEntity<Object> handleException(Exception ex, WebRequest request) {
        return new ResponseEntity(
            "Error: " + ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST
        );
    }

}
