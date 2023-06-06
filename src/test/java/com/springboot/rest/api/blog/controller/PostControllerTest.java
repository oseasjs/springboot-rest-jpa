package com.springboot.rest.api.blog.controller;

import static com.springboot.rest.api.blog.utils.TestUtils.CONTENT;
import static com.springboot.rest.api.blog.utils.TestUtils.NEW_POST_DTO_MOCKED;
import static com.springboot.rest.api.blog.utils.TestUtils.TITLE;
import static com.springboot.rest.api.blog.utils.TestUtils.formatDate;
import static com.springboot.rest.api.blog.utils.TestUtils.newPostDtoAsMap;
import static com.springboot.rest.api.blog.utils.TestUtils.newRemotePostDtoAsMap;
import static com.springboot.rest.api.blog.utils.TestUtils.now;
import static com.springboot.rest.api.blog.utils.TestUtils.requiredMsg;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;

import com.springboot.rest.api.blog.enums.GeneratedTypeEnum;
import com.springboot.rest.api.blog.feign.client.dto.JsonPlaceHolderPostDto;
import com.springboot.rest.api.blog.model.Post;

public class PostControllerTest extends AbstractControllerTest {

  private Post postMocked = new Post(BigDecimal.ONE.longValue(), TITLE, CONTENT, now, GeneratedTypeEnum.MANUAL);

  @WithAnonymousUser
  @Test
  public void notLoggedIn_shouldNotExecuteGetPostEndpoint() throws Exception {
    mockMvc.perform(get("/v1/posts")
        .accept(APPLICATION_JSON))
      .andExpect(status().isUnauthorized());
  }

  @WithMockUser("spring")
  @Test
  public void loggedIn_shouldExecuteGetPostEndpoint() throws Exception {
    mockMvc.perform(get("/v1/posts")
        .accept(APPLICATION_JSON))
      .andExpect(status().isOk());
  }

  @WithMockUser("spring")
  @Test
  public void shouldReturnFoundPostSuccessfully() throws Exception {

    when(postService.findById(1L)).thenReturn(postMocked);

    mockMvc.perform(get("/v1/posts/1").accept(APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(content().contentType(APPLICATION_JSON))
      .andExpect(jsonPath("$.id", is(postMocked.getId().intValue())))
      .andExpect(jsonPath("$.title", is(postMocked.getTitle())))
      .andExpect(jsonPath("$.content", is(postMocked.getContent())))
      .andExpect(jsonPath("$.creationDate", is(formatDate(postMocked.getCreationDate()))))
      .andExpect(jsonPath("$.generatedType", is(postMocked.getGeneratedType().toString())));

  }

  @WithMockUser("spring")
  @Test
  public void shouldAddPostSuccessfully() throws Exception {

    when(postService.add(any())).thenReturn(postMocked);

    mockMvc.perform(post("/v1/posts")
        .content(jsonUtil.mapToJson(newPostDtoAsMap()))
        .contentType(APPLICATION_JSON)
        .accept(APPLICATION_JSON)
        .with(SecurityMockMvcRequestPostProcessors.jwt())
      )
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.id", is(postMocked.getId().intValue())))
      .andExpect(jsonPath("$.title", is(postMocked.getTitle())))
      .andExpect(jsonPath("$.content", is(postMocked.getContent())))
      .andExpect(jsonPath("$.creationDate", is(formatDate(postMocked.getCreationDate()))))
      .andExpect(jsonPath("$.generatedType", is(postMocked.getGeneratedType().toString())));
  }

  @WithMockUser("spring")
  @Test
  public void shouldAddPostMissingTitleSuccessfully() throws Exception {

    when(postService.add(any())).thenReturn(postMocked);

    Map<String, Object> newPostDtoMap = newPostDtoAsMap();
    newPostDtoMap.remove("title");

    mockMvc.perform(post("/v1/posts")
        .content(jsonUtil.mapToJson(newPostDtoMap))
        .contentType(APPLICATION_JSON)
        .accept(APPLICATION_JSON))
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.validationMessage.title", containsString(requiredMsg("Title"))));
  }

  @WithMockUser("spring")
  @Test
  public void shouldAddPostMissingContentSuccessfully() throws Exception {

    when(postService.add(any())).thenReturn(postMocked);

    Map<String, Object> newPostDtoMap = newPostDtoAsMap();
    newPostDtoMap.remove("content");

    mockMvc.perform(post("/v1/posts")
        .content(jsonUtil.mapToJson(newPostDtoMap))
        .contentType(APPLICATION_JSON)
        .accept(APPLICATION_JSON))
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.validationMessage.content", containsString(requiredMsg("Content"))));
  }

  @WithMockUser("spring")
  @Test
  public void shouldAddPostMissingCreationDateSuccessfully() throws Exception {

    when(postService.add(any())).thenReturn(postMocked);

    Map<String, Object> newPostDtoMap = newPostDtoAsMap();
    newPostDtoMap.remove("creationDate");

    mockMvc.perform(post("/v1/posts")
        .content(jsonUtil.mapToJson(newPostDtoMap))
        .contentType(APPLICATION_JSON)
        .accept(APPLICATION_JSON))
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.validationMessage.creationDate", containsString(requiredMsg("Creation Date"))));
  }

  @WithMockUser("spring")
  @Test
  public void shouldAddPostMissingAllFieldDateSuccessfully() throws Exception {

    when(postService.add(any())).thenReturn(postMocked);

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

  @WithMockUser("spring")
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
    when(postService.add(any())).thenReturn(postMocked);

    mockMvc.perform(post("/v1/posts/remotes")
        .content(jsonUtil.mapToJson(newRemotePostDtoAsMap()))
        .contentType(APPLICATION_JSON)
        .accept(APPLICATION_JSON))
      .andExpect(status().isCreated());
  }

  @WithMockUser("spring")
  @Test
  public void shouldAddRemotePostMissingLimitSuccessfully() throws Exception {

    mockMvc.perform(post("/v1/posts/remotes")
        .content(jsonUtil.mapToJson(new HashMap()))
        .contentType(APPLICATION_JSON)
        .accept(APPLICATION_JSON))
      .andExpect(status().isBadRequest());
  }

}