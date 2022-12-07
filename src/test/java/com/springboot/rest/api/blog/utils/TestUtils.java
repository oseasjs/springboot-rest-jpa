package com.springboot.rest.api.blog.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.springboot.rest.api.blog.controller.dto.CommentDto;
import com.springboot.rest.api.blog.controller.dto.NewCommentDto;
import com.springboot.rest.api.blog.controller.dto.NewPostDto;
import com.springboot.rest.api.blog.controller.dto.PostDto;
import com.springboot.rest.api.blog.enums.GeneratedTypeEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public abstract class TestUtils {

    public static final LocalDateTime now = LocalDateTime.now();
    public static final String TITLE = "title";
    public static final String CONTENT = "content";
    public static final String AUTHOR = "author";

    public static final NewPostDto NEW_POST_DTO_MOCKED = NewPostDto
        .builder()
        .title(TITLE)
        .content(CONTENT)
        .creationDate(now)
        .build();

    public static final PostDto POST_DTO_MOCKED = PostDto
      .builder()
      .id(BigDecimal.ONE.longValue())
      .title(TITLE)
      .content(CONTENT)
      .creationDate(now)
      .generatedType(GeneratedTypeEnum.MANUAL)
        .build();

    public static final NewCommentDto NEW_COMMENT_DTO_MOCKED = NewCommentDto
        .builder()
        .content(CONTENT)
        .author(AUTHOR)
        .creationDate(now)
        .build();

  public static final CommentDto COMMENT_DTO_MOCKED = CommentDto
    .builder()
    .id(BigDecimal.ONE.longValue())
    .content(CONTENT)
    .author(AUTHOR)
    .creationDate(now)
    .generatedType(GeneratedTypeEnum.MANUAL)
    .build();

  public static Map<String, Object> newPostDtoAsMap() {
    Map<String, Object> elements = new HashMap();
    elements.put("title", NEW_POST_DTO_MOCKED.getTitle());
    elements.put("content", NEW_POST_DTO_MOCKED.getContent());
    elements.put("creationDate", NEW_POST_DTO_MOCKED.getCreationDate().toString());
    return elements;
  }

  public static Map<String, Object> newCommentDtoAsMap() {
    Map<String, Object> elements = new HashMap();
    elements.put("content", NEW_COMMENT_DTO_MOCKED.getContent());
    elements.put("author", NEW_COMMENT_DTO_MOCKED.getAuthor());
    elements.put("creationDate", NEW_POST_DTO_MOCKED.getCreationDate().toString());
    return elements;
  }

  public static Map<String, Object> newRemotePostDtoAsMap() {
    Map<String, Object> elements = new HashMap();
    elements.put("limit", BigDecimal.ONE.intValue());
    return elements;
  }

  public static Map<String, Object> newRemoteCommentDtoAsMap() {
    Map<String, Object> elements = new HashMap();
    elements.put("limit", BigDecimal.ONE.intValue());
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

    public static String formatDate(LocalDateTime date) {
        return date.format(DateTimeFormatter.ISO_DATE_TIME);
    }

}
