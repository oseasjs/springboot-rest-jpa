package com.springboot.rest.api.blog.service;

import com.springboot.rest.api.blog.exception.NotFoundException;
import com.springboot.rest.api.blog.model.Comment;
import com.springboot.rest.api.blog.repository.CommentRepository;
import com.springboot.rest.api.blog.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentService {
    private PostRepository postRepository;
    private CommentRepository commentRepository;

    public List<Comment> getCommentsForPost(Long postId, Pageable pageable) {
        return this.commentRepository.findByPostId(postId, pageable);
    }

    public boolean existsByPostIdAndAuthor(Long postId, String author) {
        return this.commentRepository.existsByPostIdAndAuthor(postId, author);
    }

    public Long addComment(Comment comment) {

        this.postRepository
            .findById(comment.getPost().getId())
            .ifPresentOrElse(
                comment::setPost,
                () -> {
                    throw new NotFoundException(String.format("Post not found with ID = %d", comment.getPost().getId()));
                }
            );

        return this.commentRepository
            .save(comment)
            .getId();
    }
}
