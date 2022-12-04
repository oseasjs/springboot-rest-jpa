package com.springboot.rest.api.blog.feign.client;

import com.springboot.rest.api.blog.controller.dto.RemoteCommentDto;
import com.springboot.rest.api.blog.controller.dto.RemotePostDto;
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

    public void addRemotePosts(RemotePostDto remotePostDto) {
        log.info("Getting {} posts from jplaceholder", remotePostDto.getLimit());
        jsonPlaceHolderClient
          .getPosts()
          .stream()
          .filter(p -> !postService.existsByTitle(p.getTitle()))
          .limit(remotePostDto.getLimit())
          .map(PostMapper.INSTANCE::toEntity)
          .forEach(postService::addPost);
    }

    public void addRemoteComments(Long postId, RemoteCommentDto remoteCommentDto) {
        log.info("Getting {} comments from jplaceholder", remoteCommentDto.getLimit());
        jsonPlaceHolderClient
          .getComments(postId)
          .stream()
          .filter(c -> !commentService.existsByPostIdAndAuthor(c.getPostId(), c.getAuthor()))
          .limit(remoteCommentDto.getLimit())
          .map(CommentMapper.INSTANCE::toEntity)
          .forEach(commentService::addComment);
    }
}
