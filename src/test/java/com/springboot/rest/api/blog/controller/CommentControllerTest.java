package com.springboot.rest.api.blog.controller;

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

    private Post postMocked = new Post(BigDecimal.ONE.longValue(), TITLE, CONTENT, now);
    private Comment commentMocked = new Comment(BigDecimal.ONE.longValue(), CONTENT, AUTHOR, now, postMocked);

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
            .andExpect(jsonPath("$[0].creationDate", is(formatDate(commentMocked.getCreationDate()))));

    }

    @Test
    public void shouldAddCommentSuccessfully() throws Exception {

        when(commentService.addComment(any())).thenReturn(BigDecimal.ONE.longValue());

        mockMvc.perform(post("/v1/posts/1/comments")
                .content(json(newCommentDtoAsMap()))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
            .andExpect(status().isCreated());
    }

    @Test
    public void shouldAddCommentMissingContentSuccessfully() throws Exception {
        Map<String, Object> newPostDtoMap = newCommentDtoAsMap();
        newPostDtoMap.remove("content");

        mockMvc.perform(post("/v1/posts/1/comments")
                .content(json(newPostDtoMap))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.content", containsString(requiredMsg("Content"))));
    }

    @Test
    public void shouldAddCommentMissingAuthorSuccessfully() throws Exception {
        Map<String, Object> newPostDtoMap = newCommentDtoAsMap();
        newPostDtoMap.remove("author");

        mockMvc.perform(post("/v1/posts/1/comments")
                .content(json(newPostDtoMap))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.author", containsString(requiredMsg("Author"))));
    }

    @Test
    public void shouldAddCommentMissingCreationSuccessfully() throws Exception {
        Map<String, Object> newPostDtoMap = newCommentDtoAsMap();
        newPostDtoMap.remove("creationDate");

        mockMvc.perform(post("/v1/posts/1/comments")
                .content(json(newPostDtoMap))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.creationDate", containsString(requiredMsg("Creation Date"))));
    }

    @Test
    public void shouldAddCommentMissingAllFieldsSuccessfully() throws Exception {
        mockMvc.perform(post("/v1/posts/1/comments")
                .content(json(new HashMap<>()))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.content", containsString(requiredMsg("Content"))))
            .andExpect(jsonPath("$.author", containsString(requiredMsg("Author"))))
            .andExpect(jsonPath("$.creationDate", containsString(requiredMsg("Creation Date"))));
    }

}
