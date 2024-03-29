package com.springboot.rest.api.blog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.rest.api.blog.controller.dto.CommentDto;
import com.springboot.rest.api.blog.controller.dto.NewCommentDto;
import com.springboot.rest.api.blog.controller.dto.NewRemoteCommentDto;
import com.springboot.rest.api.blog.controller.mapper.CommentMapper;
import com.springboot.rest.api.blog.feign.client.JsonPlaceHolderService;
import com.springboot.rest.api.blog.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/posts/{postId}/comments")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Comments API")
@AllArgsConstructor
@Slf4j
public class CommentController {

  private CommentService commentService;
  private JsonPlaceHolderService jsonPlaceHolderService;
  private ObjectMapper objectMapper;

  @Operation(summary = "Get All Comments from a Post", responses = {
    @ApiResponse(responseCode = "200",
      description = "Ok",
      content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class))
    ),
    @ApiResponse(responseCode = "400", description = "Bad Request")
  })
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<CommentDto> getCommentsForPost(@PathVariable Long postId,
                                             @RequestParam(defaultValue = "5", required = false) Integer pageSize,
                                             @RequestParam(defaultValue = "0", required = false) Integer page) {
    log.debug("Getting comments with postId {}", postId);
    return
      Optional
        .of(this.commentService.getCommentsForPost(postId, PageRequest.of(page, pageSize)))
        .map(CommentMapper.INSTANCE::asDtoList)
        .get();
  }

  @Operation(summary = "Add new Comment", responses = {
    @ApiResponse(responseCode = "201",
      description = "Ok",
      content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentDto.class))),
    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)
  })
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CommentDto addComment(@PathVariable Long postId, @Valid @RequestBody NewCommentDto newCommentDto) {
    newCommentDto.setPostId(postId);
    log.debug("Adding comment with postId {}", postId);
    return CommentMapper.INSTANCE.toDTO(
      this
        .commentService
        .addComment(CommentMapper.INSTANCE.toEntity(newCommentDto))
    );
  }

  @Operation(summary = "Add new Comments on existing Posts from Json Place Holder public API", responses = {
    @ApiResponse(responseCode = "201",
      description = "Ok",
      content = @Content(mediaType = "application/json")),
    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)
  })
  @PostMapping("/remotes")
  @ResponseStatus(HttpStatus.CREATED)
  public void addRemoteComments(@PathVariable Long postId, @Valid @RequestBody NewRemoteCommentDto newRemoteCommentDto) {
    log.debug("Adding {} comments with postId {} from Json Place Holder", newRemoteCommentDto.getLimit(), postId);
    jsonPlaceHolderService.addRemoteComments(postId, newRemoteCommentDto);
  }

}
