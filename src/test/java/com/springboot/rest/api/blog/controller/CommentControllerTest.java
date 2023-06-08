package com.springboot.rest.api.blog.controller;

import static com.springboot.rest.api.blog.utils.TestUtils.AUTHOR;
import static com.springboot.rest.api.blog.utils.TestUtils.CONTENT;
import static com.springboot.rest.api.blog.utils.TestUtils.TITLE;
import static com.springboot.rest.api.blog.utils.TestUtils.formatDate;
import static com.springboot.rest.api.blog.utils.TestUtils.newCommentDtoAsMap;
import static com.springboot.rest.api.blog.utils.TestUtils.newRemoteCommentDtoAsMap;
import static com.springboot.rest.api.blog.utils.TestUtils.now;
import static com.springboot.rest.api.blog.utils.TestUtils.requiredMsg;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.test.context.support.WithMockUser;

import com.springboot.rest.api.blog.enums.GeneratedTypeEnum;
import com.springboot.rest.api.blog.feign.client.dto.JsonPlaceHolderCommentDto;
import com.springboot.rest.api.blog.model.Comment;
import com.springboot.rest.api.blog.model.Post;

public class CommentControllerTest extends AbstractControllerTest {

  private Post postMocked = new Post(BigDecimal.ONE.longValue(), TITLE, CONTENT, now, GeneratedTypeEnum.MANUAL, null, null);
  private Comment commentMocked = new Comment(BigDecimal.ONE.longValue(), CONTENT, AUTHOR, now, GeneratedTypeEnum.MANUAL, postMocked, null, null);

  @Test
  public void notLoggedIn_shouldNotExecuteGetCommentsEndpoint() throws Exception {
    mockMvc.perform(get("/v1/posts/1/comments")
        .accept(APPLICATION_JSON))
      .andExpect(status().isUnauthorized());
  }

  @WithMockUser
  @Test
  public void shouldReturnFoundCommentsSuccessfully() throws Exception {

    when(commentService.getCommentsForPost(BigDecimal.ONE.longValue(), PageRequest.of(0, 5))).thenReturn(List.of(commentMocked));

    mockMvc.perform(get("/v1/posts/1/comments?page=0&pageSize=5").accept(APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$", hasSize(1)))
      .andExpect(jsonPath("$[0].id", is(commentMocked.getId().intValue())))
      .andExpect(jsonPath("$[0].postId", is(commentMocked.getPost().getId().intValue())))
      .andExpect(jsonPath("$[0].content", is(commentMocked.getContent())))
      .andExpect(jsonPath("$[0].author", is(commentMocked.getAuthor())))
      .andExpect(jsonPath("$[0].creationDate", is(formatDate(commentMocked.getCreationDate()))))
      .andExpect(jsonPath("$[0].generatedType", is(commentMocked.getGeneratedType().toString())));

  }

  @WithMockUser
  @Test
  public void shouldAddCommentSuccessfully() throws Exception {

    when(commentService.addComment(any())).thenReturn(commentMocked);

    mockMvc.perform(post("/v1/posts/1/comments")
        .content(jsonUtil.mapToJson(newCommentDtoAsMap()))
        .contentType(APPLICATION_JSON)
        .accept(APPLICATION_JSON))
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.id", is(commentMocked.getId().intValue())))
      .andExpect(jsonPath("$.postId", is(commentMocked.getPost().getId().intValue())))
      .andExpect(jsonPath("$.content", is(commentMocked.getContent())))
      .andExpect(jsonPath("$.author", is(commentMocked.getAuthor())))
      .andExpect(jsonPath("$.creationDate", is(formatDate(commentMocked.getCreationDate()))))
      .andExpect(jsonPath("$.generatedType", is(commentMocked.getGeneratedType().toString())));
    ;
  }

  @WithMockUser
  @Test
  public void shouldAddCommentMissingContentSuccessfully() throws Exception {
    Map<String, Object> newPostDtoMap = newCommentDtoAsMap();
    newPostDtoMap.remove("content");

    mockMvc.perform(post("/v1/posts/1/comments")
        .content(jsonUtil.mapToJson(newPostDtoMap))
        .contentType(APPLICATION_JSON)
        .accept(APPLICATION_JSON))
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.validationMessage.content", containsString(requiredMsg("Content"))));
  }

  @WithMockUser
  @Test
  public void shouldAddCommentMissingAuthorSuccessfully() throws Exception {
    Map<String, Object> newPostDtoMap = newCommentDtoAsMap();
    newPostDtoMap.remove("author");

    mockMvc.perform(post("/v1/posts/1/comments")
        .content(jsonUtil.mapToJson(newPostDtoMap))
        .contentType(APPLICATION_JSON)
        .accept(APPLICATION_JSON))
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.validationMessage.author", containsString(requiredMsg("Author"))));
  }

  @WithMockUser
  @Test
  public void shouldAddCommentMissingCreationSuccessfully() throws Exception {
    Map<String, Object> newPostDtoMap = newCommentDtoAsMap();
    newPostDtoMap.remove("creationDate");

    mockMvc.perform(post("/v1/posts/1/comments")
        .content(jsonUtil.mapToJson(newPostDtoMap))
        .contentType(APPLICATION_JSON)
        .accept(APPLICATION_JSON))
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.validationMessage.creationDate", containsString(requiredMsg("Creation Date"))));
  }

  @WithMockUser
  @Test
  public void shouldAddCommentMissingAllFieldsSuccessfully() throws Exception {
    mockMvc.perform(post("/v1/posts/1/comments")
        .content(jsonUtil.mapToJson(new HashMap<>()))
        .contentType(APPLICATION_JSON)
        .accept(APPLICATION_JSON))
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.validationMessage.content", containsString(requiredMsg("Content"))))
      .andExpect(jsonPath("$.validationMessage.author", containsString(requiredMsg("Author"))))
      .andExpect(jsonPath("$.validationMessage.creationDate", containsString(requiredMsg("Creation Date"))));
  }

  @WithMockUser
  @Test
  public void shouldAddRemoteCommentsSuccessfully() throws Exception {
    when(jsonPlaceHolderClient.getComments(any()))
      .thenReturn(
        List.of(
          JsonPlaceHolderCommentDto.builder()
            .author(AUTHOR)
            .content(CONTENT)
            .creationDate(now)
            .postId(BigDecimal.ONE.longValue())
            .build()
        )
      );

    when(commentService.addComment(any())).thenReturn(commentMocked);

    mockMvc.perform(post("/v1/posts/1/comments/remotes")
        .content(jsonUtil.mapToJson(newRemoteCommentDtoAsMap()))
        .contentType(APPLICATION_JSON)
        .accept(APPLICATION_JSON))
      .andExpect(status().isCreated());
  }

  @WithMockUser
  @Test
  public void shouldAddRemoteCommentsMissingLimitSuccessfully() throws Exception {
    mockMvc.perform(post("/v1/posts/1/comments/remotes")
        .content(jsonUtil.mapToJson(new HashMap()))
        .contentType(APPLICATION_JSON)
        .accept(APPLICATION_JSON))
      .andExpect(status().isBadRequest());
  }

}