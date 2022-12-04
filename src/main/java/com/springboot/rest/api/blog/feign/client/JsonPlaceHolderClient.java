package com.springboot.rest.api.blog.feign.client;

import com.springboot.rest.api.blog.feign.client.dto.JsonPlaceHolderCommentDto;
import com.springboot.rest.api.blog.feign.client.dto.JsonPlaceHolderPostDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "jplaceholder", url = "https://jsonplaceholder.typicode.com")
public interface JsonPlaceHolderClient {

    Logger LOGGER =  LoggerFactory.getLogger(JsonPlaceHolderClient.class);

    @RequestMapping(method = RequestMethod.GET, value = "/posts")
    public List<JsonPlaceHolderPostDto> getPosts();

    @RequestMapping(method = RequestMethod.GET, value = "/posts/{postId}/comments")
    public List<JsonPlaceHolderCommentDto> getComments(
        @PathVariable("postId") Long postId);

}
