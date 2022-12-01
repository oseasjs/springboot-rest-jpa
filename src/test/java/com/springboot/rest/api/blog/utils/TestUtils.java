package com.springboot.rest.api.blog.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.springboot.rest.api.blog.controller.dto.CommentDto;
import com.springboot.rest.api.blog.controller.dto.NewCommentDto;
import com.springboot.rest.api.blog.controller.dto.NewPostDto;
import com.springboot.rest.api.blog.controller.dto.PostDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public abstract class TestUtils {

    public static final LocalDateTime now = LocalDateTime.now();
    public static final String TITLE = "title";
    public static final String CONTENT = "content";
    public static final String AUTHOR = "author";

    public static final NewPostDto NEW_POST_MOCKED = NewPostDto
        .builder()
        .title(TITLE)
        .content(CONTENT)
        .creationDate(now)
        .build();

    public static final PostDto POST_MOCKED = PostDto
        .builder()
        .id(BigDecimal.ONE.longValue())
        .title(NEW_POST_MOCKED.getTitle())
        .content(NEW_POST_MOCKED.getContent())
        .creationDate(NEW_POST_MOCKED.getCreationDate())
        .build();

    public static final NewCommentDto NEW_COMMENT_MOCKED = NewCommentDto
        .builder()
        .content(CONTENT)
        .author(AUTHOR)
        .creationDate(now)
        .build();

    public static final CommentDto COMMENT_MOCKED = CommentDto
        .builder()
        .id(BigDecimal.ONE.longValue())
        .content(NEW_COMMENT_MOCKED.getContent())
        .author(NEW_COMMENT_MOCKED.getAuthor())
        .creationDate(NEW_COMMENT_MOCKED.getCreationDate())
        .build();

    public static Map<String, Object> newPostDtoAsMap() {
        Map<String, Object> elements = new HashMap();
        elements.put("title", NEW_POST_MOCKED.getTitle());
        elements.put("content", NEW_POST_MOCKED.getContent());
        elements.put("creationDate", NEW_POST_MOCKED.getCreationDate().toString());
        return elements;
    }

    public static Map<String, Object> newCommentDtoAsMap() {
        Map<String, Object> elements = new HashMap();
        elements.put("content", NEW_COMMENT_MOCKED.getContent());
        elements.put("author", NEW_COMMENT_MOCKED.getAuthor());
        elements.put("creationDate", NEW_POST_MOCKED.getCreationDate().toString());
        return elements;
    }

    public static String json(Map<String, Object> elements) {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        try {
            return objectMapper.writeValueAsString(elements);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String requiredMsg(String field) {
        return String.format("%s is required", field);
    }

}
