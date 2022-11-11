package com.springboot.rest.api.blog.service;

import com.springboot.rest.api.blog.dto.CommentDto;
import com.springboot.rest.api.blog.dto.NewCommentDto;
import com.springboot.rest.api.blog.model.Comment;
import com.springboot.rest.api.blog.model.Post;
import com.springboot.rest.api.blog.repository.CommentRepository;
import com.springboot.rest.api.blog.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private PostRepository postRepository;
    private CommentRepository commentRepository;

    public CommentService(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    public List<CommentDto> getCommentsForPost(Long postId) {
        return this.commentRepository
            .findByPostId(postId)
            .stream()
            .map(comment -> new CommentDto(comment.getId(), comment.getContent(), comment.getAuthor(), comment.getCreationDate()))
            .collect(Collectors.toList());
    }

    public Long addComment(NewCommentDto newCommentDto) {

        Post post = this.postRepository
            .findById(newCommentDto.getPostId())
            .orElseThrow(() -> new IllegalArgumentException("Post not found with ID = " + newCommentDto.getPostId()));

        return this.commentRepository
            .save(
                new Comment(null, newCommentDto.getContent(), newCommentDto.getAuthor(), post, newCommentDto.getCreationDate())
            )
            .getId();
    }
}
