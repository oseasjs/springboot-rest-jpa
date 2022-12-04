package com.springboot.rest.api.blog.repository;

import com.springboot.rest.api.blog.model.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    public List<Comment> findByPostId(Long postId, Pageable pageable);
    public boolean existsByPostIdAndAuthor(Long postId, String author);
}
