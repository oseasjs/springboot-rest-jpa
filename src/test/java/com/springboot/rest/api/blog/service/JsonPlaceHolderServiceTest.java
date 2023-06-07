package com.springboot.rest.api.blog.service;

import static com.springboot.rest.api.blog.utils.TestUtils.AUTHOR;
import static com.springboot.rest.api.blog.utils.TestUtils.CONTENT;
import static com.springboot.rest.api.blog.utils.TestUtils.TITLE;
import static com.springboot.rest.api.blog.utils.TestUtils.now;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;

import com.springboot.rest.api.blog.BaseBlogTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.springboot.rest.api.blog.controller.dto.NewRemoteCommentDto;
import com.springboot.rest.api.blog.controller.dto.NewRemotePostDto;
import com.springboot.rest.api.blog.enums.GeneratedTypeEnum;
import com.springboot.rest.api.blog.feign.client.JsonPlaceHolderClient;
import com.springboot.rest.api.blog.feign.client.JsonPlaceHolderService;
import com.springboot.rest.api.blog.feign.client.dto.JsonPlaceHolderCommentDto;
import com.springboot.rest.api.blog.feign.client.dto.JsonPlaceHolderPostDto;
import com.springboot.rest.api.blog.model.Comment;
import com.springboot.rest.api.blog.model.Post;
import com.springboot.rest.api.blog.repository.CommentRepository;
import com.springboot.rest.api.blog.repository.PostRepository;

@SpringBootTest
public class JsonPlaceHolderServiceTest extends BaseBlogTest {

  @MockBean
  protected JsonPlaceHolderClient jsonPlaceHolderClient;

  @Autowired
  private JsonPlaceHolderService jsonPlaceHolderService;
    
  @Autowired
  private PostRepository postRepository;
  
  @Autowired
  private CommentRepository commentRepository;

  private final Post postMocked = new Post(null, TITLE + " JsonPlaceHolder", CONTENT, now, GeneratedTypeEnum.MANUAL, null, null);
  private Post existingPost;

  @BeforeEach
  public void setup() {
    existingPost = postRepository.save(postMocked);
  }

  @AfterEach
  public void tearDown() {
    commentRepository
      .findAll()
      .forEach(comment -> commentRepository.delete(comment));

    postRepository
      .findAll()
      .forEach(p -> postRepository.delete(p));
  }


  @Test
  public void getRemotePostsSuccessfully() {

    final String NEW_TITLE = TITLE + UUID.randomUUID();

    when(jsonPlaceHolderClient.getPosts())
      .thenReturn(
        List.of(
          JsonPlaceHolderPostDto.builder()
            .title(NEW_TITLE)
            .content(CONTENT)
            .creationDate(now)
            .build()
        )
      );

    jsonPlaceHolderService.addRemotePosts(
      NewRemotePostDto
        .builder()
        .limit(1)
        .build()
    );

    Post postFound = postRepository
      .findAll()
      .stream()
      .filter(p -> p.getTitle().equals(NEW_TITLE))
      .iterator()
      .next();

    assertNotNull(postFound, "Post shouldn't be null");
    assertEquals(postFound.getTitle(), NEW_TITLE);
    assertEquals(postFound.getContent(), CONTENT);
    assertEquals(postFound.getCreationDate(), now);
    assertEquals(postFound.getGeneratedType(), GeneratedTypeEnum.AUTO);

  }

  @Test
  public void getRemoteCommentsSuccessfully() {

    when(jsonPlaceHolderClient.getComments(any()))
      .thenReturn(
        List.of(
          JsonPlaceHolderCommentDto.builder()
            .author(AUTHOR)
            .content(CONTENT)
            .creationDate(now)
            .postId(existingPost.getId())
            .build()
        )
      );

    jsonPlaceHolderService.addRemoteComments(
      existingPost.getId(),
      NewRemoteCommentDto
        .builder()
        .limit(1)
        .build()
    );

    Comment commentFound = commentRepository
      .findAll()
      .iterator()
      .next();

    assertNotNull(commentFound, "Comment shouldn't be null");
    assertEquals(commentFound.getContent(), CONTENT);
    assertEquals(commentFound.getAuthor(), AUTHOR);
    assertEquals(commentFound.getCreationDate(), now);
    assertEquals(commentFound.getGeneratedType(), GeneratedTypeEnum.AUTO);

  }

}
