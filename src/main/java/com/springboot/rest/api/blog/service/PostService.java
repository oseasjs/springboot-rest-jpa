package com.springboot.rest.api.blog.service;

import com.springboot.rest.api.blog.exception.NotFoundException;
import com.springboot.rest.api.blog.model.Post;
import com.springboot.rest.api.blog.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PostService {

  private PostRepository postRepository;

  public Post getPost(Long id) {
    return postRepository
      .findById(id)
      .orElseThrow(() -> new NotFoundException(String.format("Post not found with ID = %d", id)));
  }

  public Post addPost(Post post) {
    return postRepository.save(post);
  }

  public boolean existsByTitle(String title) {
    return postRepository.existsByTitle(title);
  }

}
