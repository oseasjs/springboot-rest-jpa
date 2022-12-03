package com.springboot.rest.api.blog.controller;

import com.springboot.rest.api.blog.controller.dto.CommentDto;
import com.springboot.rest.api.blog.controller.dto.NewCommentDto;
import com.springboot.rest.api.blog.controller.mapper.CommentMapper;
import com.springboot.rest.api.blog.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/posts/{postId}/comments")
@Api(value = "Post Comments API for the Blog")
@AllArgsConstructor
public class CommentController {

    private CommentService commentService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get All Comments from a Post", produces = "application/json")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Ok"),
        @ApiResponse(code = 400, message = "Bad Request")
    })
    public List<CommentDto> getCommentsForPost(@PathVariable Long postId,
                                               @RequestParam(defaultValue = "5", required = false) Integer pageSize,
                                               @RequestParam(defaultValue = "0", required = false) Integer page) {
        return
            Optional
                .of(this.commentService.getCommentsForPost(postId, PageRequest.of(page, pageSize)))
                .map(CommentMapper.INSTANCE::asDtoList)
                .get();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Add new Comment", produces = "application/json")
    @ApiResponses({
        @ApiResponse(code = 201, message = "Created"),
        @ApiResponse(code = 400, message = "Bad Request")
    })
    public Long addComment(@PathVariable Long postId, @Valid @RequestBody NewCommentDto newCommentDto) {
        newCommentDto.setPostId(postId);
        return this
            .commentService
            .addComment(CommentMapper.INSTANCE.toEntity(newCommentDto));
    }

}
