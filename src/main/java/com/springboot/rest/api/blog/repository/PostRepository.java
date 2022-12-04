package com.springboot.rest.api.blog.repository;

import com.springboot.rest.api.blog.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    public boolean existsByTitle(String title);
}
