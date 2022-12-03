package com.springboot.rest.api.blog.controller;

import com.springboot.rest.api.blog.controller.dto.CommentDto;
import com.springboot.rest.api.blog.controller.dto.NewCommentDto;
import com.springboot.rest.api.blog.controller.mapper.CommentMapper;
import com.springboot.rest.api.blog.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/posts/{postId}/comments")
@AllArgsConstructor
public class CommentController {

    private CommentService commentService;

    @Operation(summary = "Get All Comments from a Post", responses = {
        @ApiResponse(responseCode = "200",
            description = "Ok",
            content = @Content(mediaType = "application/json",schema = @Schema(implementation = List.class))
        ),
        @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CommentDto> getCommentsForPost(@PathVariable Long postId,
                                               @RequestParam(defaultValue = "5", required = false) Integer pageSize,
                                               @RequestParam(defaultValue = "0", required = false) Integer page) {
        return
            Optional
                .of(this.commentService.getCommentsForPost(postId, PageRequest.of(page, pageSize)))
                .map(CommentMapper.INSTANCE::asDtoList)
                .get();
    }

    @Operation(summary = "Add new Comment", responses = {
        @ApiResponse(responseCode = "201",
            description = "Ok",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Long.class))),
        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long addComment(@PathVariable Long postId, @Valid @RequestBody NewCommentDto newCommentDto) {
        newCommentDto.setPostId(postId);
        return this
            .commentService
            .addComment(CommentMapper.INSTANCE.toEntity(newCommentDto));
    }

}
