package com.springboot.rest.api.blog.feign.client;

import com.springboot.rest.api.blog.controller.dto.NewRemoteCommentDto;
import com.springboot.rest.api.blog.controller.dto.NewRemotePostDto;
import com.springboot.rest.api.blog.controller.mapper.CommentMapper;
import com.springboot.rest.api.blog.controller.mapper.PostMapper;
import com.springboot.rest.api.blog.service.CommentService;
import com.springboot.rest.api.blog.service.PostService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class JsonPlaceHolderService {
  private JsonPlaceHolderClient jsonPlaceHolderClient;
  private PostService postService;
  private CommentService commentService;

  public void addRemotePosts(NewRemotePostDto newRemotePostDto) {
    log.debug("Getting {} posts from Json Place Holder", newRemotePostDto.getLimit());
    jsonPlaceHolderClient
      .getPosts()
      .stream()
      .filter(p -> !postService.existsByTitle(p.getTitle()))
      .limit(newRemotePostDto.getLimit())
      .map(PostMapper.INSTANCE::toEntity)
      .forEach(postService::add);
    log.debug("{} Posts saved from Json Place Holder", newRemotePostDto.getLimit());
  }

  public void addRemoteComments(Long postId, NewRemoteCommentDto newRemoteCommentDto) {
    log.info("Getting {} comments from Json Place Holder", newRemoteCommentDto.getLimit());
    jsonPlaceHolderClient
      .getComments(postId)
      .stream()
      .filter(c -> !commentService.existsByPostIdAndAuthor(c.getPostId(), c.getAuthor()))
      .limit(newRemoteCommentDto.getLimit())
      .map(CommentMapper.INSTANCE::toEntity)
      .forEach(commentService::addComment);
    log.info("{} Comments saved from Json Place Holder", newRemoteCommentDto.getLimit());
  }
}
