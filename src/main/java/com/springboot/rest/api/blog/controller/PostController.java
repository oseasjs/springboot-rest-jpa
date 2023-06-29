package com.springboot.rest.api.blog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.rest.api.blog.controller.dto.NewPostDto;
import com.springboot.rest.api.blog.controller.dto.NewRemotePostDto;
import com.springboot.rest.api.blog.controller.dto.PostDto;
import com.springboot.rest.api.blog.controller.mapper.PostMapper;
import com.springboot.rest.api.blog.enums.KafkaTopicEnum;
import com.springboot.rest.api.blog.feign.client.JsonPlaceHolderService;
import com.springboot.rest.api.blog.kafka.dto.NewRemotePostAsyncDto;
import com.springboot.rest.api.blog.kafka.producer.KafkaProducerService;
import com.springboot.rest.api.blog.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/posts")
@Tag(name = "Post API")
@SecurityRequirement(name = "Bearer Authentication")
@AllArgsConstructor
@Slf4j
public class PostController {
  private PostService postService;
  private JsonPlaceHolderService jsonPlaceHolderService;
  private KafkaProducerService kafkaProducerService;
  private ObjectMapper objectMapper;

  @Operation(summary = "Get All Posts", responses = {
    @ApiResponse(responseCode = "200",
      description = "Ok",
      content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostDto.class))
    ),
    @ApiResponse(responseCode = "400", description = "Bad Request")
  })
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<PostDto> findAllPost() {
    log.debug("Getting all post");
    return Optional
      .of(this.postService.findAll())
      .map(PostMapper.INSTANCE::asDtoList)
      .get();
  }

  @Operation(summary = "Get Post by ID", responses = {
    @ApiResponse(responseCode = "200",
      description = "Ok",
      content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostDto.class))
    ),
    @ApiResponse(responseCode = "400", description = "Bad Request")
  })
  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public PostDto findById(@PathVariable Long id) {
    log.debug("Getting post with id {}", id);
    return Optional
      .of(this.postService.findById(id))
      .map(PostMapper.INSTANCE::toDTO)
      .get();
  }

  @Operation(summary = "Add new Post", responses = {
    @ApiResponse(responseCode = "201",
      description = "Ok",
      content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostDto.class))),
    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)
  })
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public PostDto add(@Valid @RequestBody NewPostDto newPostDto) {
    log.debug("Adding post");
    return PostMapper.INSTANCE.toDTO(
      this.postService
        .add(PostMapper.INSTANCE.toEntity(newPostDto))
    );
  }

  @Operation(summary = "Add news Posts from Json Place Holder public API", responses = {
    @ApiResponse(responseCode = "201",
      description = "Ok",
      content = @Content(mediaType = "application/json")),
    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)
  })
  @PostMapping("/remotes")
  @ResponseStatus(HttpStatus.CREATED)
  public void addRemotePosts(@Valid @RequestBody NewRemotePostDto newRemotePostDto) {
    log.debug("Adding {} posts from Json Place Holder", newRemotePostDto.getLimit());
    jsonPlaceHolderService.addRemotePosts(newRemotePostDto);
  }

  @Operation(summary = "Add news Posts from Json Place Holder public API ASYNCHRONOUSLY", responses = {
          @ApiResponse(responseCode = "201",
                  description = "Ok",
                  content = @Content(mediaType = "application/json")),
          @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)
  })
  @PostMapping("/remotes/async")
  @ResponseStatus(HttpStatus.CREATED)
  public void addRemotePostsAsync(@Valid @RequestBody NewRemotePostDto newRemotePostDto) throws Exception {
    log.debug("Adding {} posts from Json Place Holder Async", newRemotePostDto.getLimit());
    kafkaProducerService.sendPost(KafkaTopicEnum.POST, new NewRemotePostAsyncDto(newRemotePostDto));
  }

}
