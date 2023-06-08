package com.springboot.rest.api.blog.service;

import com.springboot.rest.api.blog.BaseBlogTest;
import com.springboot.rest.api.blog.enums.GeneratedTypeEnum;
import com.springboot.rest.api.blog.exception.NotFoundException;
import com.springboot.rest.api.blog.model.Post;
import com.springboot.rest.api.blog.repository.PostRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static com.springboot.rest.api.blog.utils.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
public class PostServiceTest extends BaseBlogTest {

  @Autowired
  private PostRepository postRepository;

  @Autowired
  private PostService postService;

  private Post existingPost;
  private Post postMocked = new Post(null, TITLE + "Post", CONTENT, now, GeneratedTypeEnum.MANUAL, null, null);

  @BeforeEach
  public void setup() {
    existingPost = postRepository.save(postMocked);
  }

  @AfterEach
  public void tearDown() {
    postRepository
      .findAll()
      .forEach(p -> postRepository.delete(p));
  }

  @Test
  public void shouldReturnCreatedPostSuccessfully() {
    Post post = postService.findById(existingPost.getId());

    assertNotNull(post, "Post shouldn't be null");
    assertEquals(post.getId(), existingPost.getId());
    assertEquals(post.getContent(), existingPost.getContent());
    assertEquals(post.getTitle(), existingPost.getTitle());
    assertEquals(post.getCreationDate(), existingPost.getCreationDate());
  }

  @Test
  public void shouldReturnExceptionForNotExistingPostSuccessfully() {
    Exception exception = Assertions.assertThrows(Exception.class, () -> {
      postService.findById(BigDecimal.ZERO.subtract(BigDecimal.ONE).longValue());
    });

    Assertions.assertEquals(NotFoundException.class, exception.getClass());
  }

  @Test
  public void shouldReturnPostForNotExistingPostSuccessfully() {
    Post postFound = postService.findById(existingPost.getId());

    assertNotNull(postFound, "Post shouldn't be null");
    assertEquals(postFound.getId(), existingPost.getId());
    assertEquals(postFound.getContent(), existingPost.getContent());
    assertEquals(postFound.getTitle(), existingPost.getTitle());
    assertEquals(postFound.getCreationDate(), existingPost.getCreationDate());

  }

  @Test
  public void shouldCreatedPostSuccessfully() {
    Long postId = postService.add(postMocked).getId();
    assertNotNull(postId, "Post ID shouldn't be null");
  }

}
