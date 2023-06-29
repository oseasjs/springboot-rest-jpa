package com.springboot.rest.api.blog.feign.client;

import com.springboot.rest.api.blog.feign.client.dto.JsonPlaceHolderCommentDto;
import com.springboot.rest.api.blog.feign.client.dto.JsonPlaceHolderPostDto;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "jplaceholder", url = "${jplaceholder.url}")
public interface JsonPlaceHolderClient {

  @Cacheable("placeHolderPostsCache")
  @RequestMapping(method = RequestMethod.GET, value = "/posts")
  public List<JsonPlaceHolderPostDto> getPosts();

  @Cacheable("placeHolderCommentsCache")
  @RequestMapping(method = RequestMethod.GET, value = "/posts/{postId}/comments")
  public List<JsonPlaceHolderCommentDto> getComments(
    @PathVariable("postId") Long postId);

}
