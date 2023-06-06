package com.springboot.rest.api.blog.service;

import com.springboot.rest.api.blog.exception.NotFoundException;
import com.springboot.rest.api.blog.model.Post;
import com.springboot.rest.api.blog.repository.PostRepository;
import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PostService {

  private PostRepository postRepository;

  public Post findById(Long id) {
    return postRepository
      .findById(id)
      .orElseThrow(() -> new NotFoundException(String.format("Post not found with ID = %d", id)));
  }

  public List<Post> findAll() {
    return postRepository.findAll();
  }

  public Post add(Post post) {
    return postRepository.save(post);
  }

  public boolean existsByTitle(String title) {
    return postRepository.existsByTitle(title);
  }

}
