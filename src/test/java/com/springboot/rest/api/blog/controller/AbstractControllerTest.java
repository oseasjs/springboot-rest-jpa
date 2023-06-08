package com.springboot.rest.api.blog.controller;

import com.springboot.rest.api.blog.BaseBlogTest;
import com.springboot.rest.api.blog.configuration.SecurityConfig;
import com.springboot.rest.api.blog.exception.UnauthorizedHandler;
import com.springboot.rest.api.blog.feign.client.JsonPlaceHolderClient;
import com.springboot.rest.api.blog.feign.client.JsonPlaceHolderService;
import com.springboot.rest.api.blog.kafka.consumer.KafkaConsumerService;
import com.springboot.rest.api.blog.kafka.producer.KafkaProducerService;
import com.springboot.rest.api.blog.security.JwtAuthenticationFilter;
import com.springboot.rest.api.blog.security.JwtUtils;
import com.springboot.rest.api.blog.service.CommentService;
import com.springboot.rest.api.blog.service.PostService;
import com.springboot.rest.api.blog.service.UserDetailsService;
import com.springboot.rest.api.blog.util.JsonUtil;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@WebMvcTest
@Import({JsonUtil.class, JwtUtils.class, JwtAuthenticationFilter.class, SecurityConfig.class, UnauthorizedHandler.class})
public abstract class AbstractControllerTest extends BaseBlogTest {

  @Autowired // Remove this annotation DI
  protected MockMvc mockMvc;

  @MockBean
  protected PostService postService;

  @MockBean
  protected CommentService commentService;

  @MockBean
  protected JsonPlaceHolderClient jsonPlaceHolderClient;

  @MockBean
  protected JsonPlaceHolderService jsonPlaceHolderService;

  @MockBean
  protected UserDetailsService userDetailsService;

  @MockBean
  protected AuthenticationManager authenticationManager;

  @MockBean
  protected KafkaProducerService kafkaProducerService;

  @MockBean
  protected KafkaConsumerService kafkaConsumerService;

  @MockBean
  protected KafkaTemplate kafkaTemplate;

  @Autowired
  protected JsonUtil jsonUtil;

  @Autowired
  private WebApplicationContext context;

  @Before
  public void setup() {
    mockMvc = MockMvcBuilders
      .webAppContextSetup(context)
      .apply(springSecurity())
      .build();
  }
  @BeforeEach
  public void beforeEach() {
    Mockito.reset(postService, commentService, jsonPlaceHolderClient, jsonPlaceHolderService);
  }

}
