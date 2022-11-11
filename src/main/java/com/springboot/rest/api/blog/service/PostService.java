package com.springboot.rest.api.blog.service;

import com.springboot.rest.api.blog.dto.PostDto;
import com.springboot.rest.api.blog.model.Post;
import com.springboot.rest.api.blog.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostService {

    private PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public PostDto getPost(Long id) {
        return postRepository
            .findById(id)
            .map(post -> new PostDto(post.getTitle(), post.getContent(), post.getCreationDate()))
            .orElse(null);
    }

    public Long addPost(PostDto postDto) {
        return postRepository
            .save(
                Optional.of(postDto)
                    .map(dto -> new Post(postDto.getTitle(), postDto.getContent(), postDto.getCreationDate()))
                    .get()
            )
            .getId();
    }

}
