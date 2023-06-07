package com.springboot.rest.api.blog.repository;

import com.springboot.rest.api.blog.model.Comment;
import com.springboot.rest.api.blog.model.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
  boolean existsByTitle(String title);
  List<Post> findByModerationDateNull(Pageable pageable);
}
