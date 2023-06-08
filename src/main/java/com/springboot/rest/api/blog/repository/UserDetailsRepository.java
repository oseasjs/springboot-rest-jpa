package com.springboot.rest.api.blog.repository;

import com.springboot.rest.api.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepository extends JpaRepository<User, Long> {
  User findByUsername(String username);
}