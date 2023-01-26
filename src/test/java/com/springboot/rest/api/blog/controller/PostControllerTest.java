package com.springboot.rest.api.blog.controller;

import com.springboot.rest.api.blog.enums.GeneratedTypeEnum;
import com.springboot.rest.api.blog.feign.client.dto.JsonPlaceHolderPostDto;
import com.springboot.rest.api.blog.model.Post;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.springboot.rest.api.blog.utils.TestUtils.*;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class PostControllerTest extends AbstractControllerTest {

  private Post postMocked = new Post(BigDecimal.ONE.longValue(), TITLE, CONTENT, now, GeneratedTypeEnum.MANUAL);

//  @WithMockUser("spring")
  @Test
  public void shouldReturnFoundPostSuccessfully() throws Exception {

    when(postService.getPost(1L)).thenReturn(postMocked);

    mockMvc.perform(get("/v1/posts/1").accept(APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(content().contentType(APPLICATION_JSON))
      .andExpect(jsonPath("$.id", is(postMocked.getId().intValue())))
      .andExpect(jsonPath("$.title", is(postMocked.getTitle())))
      .andExpect(jsonPath("$.content", is(postMocked.getContent())))
      .andExpect(jsonPath("$.creationDate", is(formatDate(postMocked.getCreationDate()))))
      .andExpect(jsonPath("$.generatedType", is(postMocked.getGeneratedType().toString())));

  }

//  @WithMockUser("spring")
  @Test
  public void shouldAddPostSuccessfully() throws Exception {

    when(postService.addPost(any())).thenReturn(postMocked);

    mockMvc.perform(post("/v1/posts")
        .content(jsonUtil.mapToJson(newPostDtoAsMap()))
        .contentType(APPLICATION_JSON)
        .accept(APPLICATION_JSON))
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.id", is(postMocked.getId().intValue())))
      .andExpect(jsonPath("$.title", is(postMocked.getTitle())))
      .andExpect(jsonPath("$.content", is(postMocked.getContent())))
      .andExpect(jsonPath("$.creationDate", is(formatDate(postMocked.getCreationDate()))))
      .andExpect(jsonPath("$.generatedType", is(postMocked.getGeneratedType().toString())));
  }

  @Test
  public void shouldAddPostMissingTitleSuccessfully() throws Exception {

    when(postService.addPost(any())).thenReturn(postMocked);

    Map<String, Object> newPostDtoMap = newPostDtoAsMap();
    newPostDtoMap.remove("title");

    mockMvc.perform(post("/v1/posts")
        .content(jsonUtil.mapToJson(newPostDtoMap))
        .contentType(APPLICATION_JSON)
        .accept(APPLICATION_JSON))
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.validationMessage.title", containsString(requiredMsg("Title"))));
  }

  @Test
  public void shouldAddPostMissingContentSuccessfully() throws Exception {

    when(postService.addPost(any())).thenReturn(postMocked);

    Map<String, Object> newPostDtoMap = newPostDtoAsMap();
    newPostDtoMap.remove("content");

    mockMvc.perform(post("/v1/posts")
        .content(jsonUtil.mapToJson(newPostDtoMap))
        .contentType(APPLICATION_JSON)
        .accept(APPLICATION_JSON))
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.validationMessage.content", containsString(requiredMsg("Content"))));
  }

  @Test
  public void shouldAddPostMissingCreationDateSuccessfully() throws Exception {

    when(postService.addPost(any())).thenReturn(postMocked);

    Map<String, Object> newPostDtoMap = newPostDtoAsMap();
    newPostDtoMap.remove("creationDate");

    mockMvc.perform(post("/v1/posts")
        .content(jsonUtil.mapToJson(newPostDtoMap))
        .contentType(APPLICATION_JSON)
        .accept(APPLICATION_JSON))
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.validationMessage.creationDate", containsString(requiredMsg("Creation Date"))));
  }

  @Test
  public void shouldAddPostMissingAllFieldDateSuccessfully() throws Exception {

    when(postService.addPost(any())).thenReturn(postMocked);

    Map<String, Object> newPostDtoMap = new HashMap<>();

    mockMvc.perform(post("/v1/posts")
        .content(jsonUtil.mapToJson(newPostDtoMap))
        .contentType(APPLICATION_JSON)
        .accept(APPLICATION_JSON))
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.validationMessage.title", containsString(requiredMsg("Title"))))
      .andExpect(jsonPath("$.validationMessage.content", containsString(requiredMsg("Content"))))
      .andExpect(jsonPath("$.validationMessage.creationDate", containsString(requiredMsg("Creation Date"))));
  }

  @Test
  public void shouldAddRemotePostSuccessfully() throws Exception {

    when(jsonPlaceHolderClient.getPosts())
      .thenReturn(
        List.of(
          JsonPlaceHolderPostDto.builder()
            .title(NEW_POST_DTO_MOCKED.getTitle())
            .content(NEW_POST_DTO_MOCKED.getContent())
            .creationDate(NEW_POST_DTO_MOCKED.getCreationDate())
            .build()
        )
      );
    when(postService.addPost(any())).thenReturn(postMocked);

    mockMvc.perform(post("/v1/posts/remotes")
        .content(jsonUtil.mapToJson(newRemotePostDtoAsMap()))
        .contentType(APPLICATION_JSON)
        .accept(APPLICATION_JSON))
      .andExpect(status().isCreated());
  }

  @Test
  public void shouldAddRemotePostMissingLimitSuccessfully() throws Exception {

    mockMvc.perform(post("/v1/posts/remotes")
        .content(jsonUtil.mapToJson(new HashMap()))
        .contentType(APPLICATION_JSON)
        .accept(APPLICATION_JSON))
      .andExpect(status().isBadRequest());
  }

}