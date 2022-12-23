package com.springboot.rest.api.blog.controller;

import com.springboot.rest.api.blog.feign.client.JsonPlaceHolderClient;
import com.springboot.rest.api.blog.feign.client.JsonPlaceHolderService;
import com.springboot.rest.api.blog.service.CommentService;
import com.springboot.rest.api.blog.service.PostService;
import com.springboot.rest.api.blog.util.JsonUtil;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
@Import(JsonUtil.class)
public abstract class AbstractControllerTest {

  @Autowired
  protected MockMvc mockMvc;

  @MockBean
  protected PostService postService;

  @MockBean
  protected CommentService commentService;

  @MockBean
  protected JsonPlaceHolderClient jsonPlaceHolderClient;

  @MockBean
  protected JsonPlaceHolderService jsonPlaceHolderService;

  @Autowired
  protected JsonUtil jsonUtil;

  @BeforeEach
  public void setUp() {
    Mockito.reset(postService, commentService, jsonPlaceHolderClient, jsonPlaceHolderService);
  }

}
