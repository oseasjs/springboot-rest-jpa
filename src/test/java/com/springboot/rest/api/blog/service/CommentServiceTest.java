package com.springboot.rest.api.blog.service;

import com.springboot.rest.api.blog.dto.PostDto;
import com.springboot.rest.api.blog.model.Post;
import com.springboot.rest.api.blog.repository.PostRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentServiceTest {
    @Autowired
    PostRepository postRepository;

    @Autowired
    PostService postService;

    @Test
    public void shouldReturnCreatedPost() {
        Post post = new Post();
        post.setTitle("Test title");
        post.setContent("Test content");
        LocalDateTime creationDate = LocalDateTime.of(2018, 5, 20, 20, 51, 16);
        post.setCreationDate(creationDate);
        postRepository.save(post);

        PostDto postDto = postService.getPost(post.getId());

        assertNotNull("Post shouldn't be null", postDto);
        assertThat(postDto.getContent(), equalTo("Test content"));
        assertThat(postDto.getTitle(), equalTo("Test title"));
        assertThat(postDto.getCreationDate(), equalTo(creationDate));
    }

    @Test
    public void shouldReturnNullForNotExistingPost() {
        PostDto postDto = postService.getPost(123L);

        assertNull(postDto);
    }
}
