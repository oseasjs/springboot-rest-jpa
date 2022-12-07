package com.springboot.rest.api.blog.controller;

import com.springboot.rest.api.blog.enums.GeneratedTypeEnum;
import com.springboot.rest.api.blog.feign.client.dto.JsonPlaceHolderCommentDto;
import com.springboot.rest.api.blog.model.Comment;
import com.springboot.rest.api.blog.model.Post;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.springboot.rest.api.blog.utils.TestUtils.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CommentControllerTest extends AbstractControllerTest {

  private Post postMocked = new Post(BigDecimal.ONE.longValue(), TITLE, CONTENT, now, GeneratedTypeEnum.MANUAL);
  private Comment commentMocked = new Comment(BigDecimal.ONE.longValue(), CONTENT, AUTHOR, now, GeneratedTypeEnum.MANUAL, postMocked);

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
            .andExpect(jsonPath("$.generatedType", is(commentMocked.getGeneratedType().toString())));;
    }

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

  @Test
  public void shouldAddRemoteCommentsMissingLimitSuccessfully() throws Exception {
    mockMvc.perform(post("/v1/posts/1/comments/remotes")
        .content(jsonUtil.mapToJson(new HashMap()))
        .contentType(APPLICATION_JSON)
        .accept(APPLICATION_JSON))
      .andExpect(status().isBadRequest());
  }

}
