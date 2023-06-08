package com.springboot.rest.api.blog.repository;

import com.springboot.rest.api.blog.model.InvalidWord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvalidWordRepository extends JpaRepository<InvalidWord, Long> {

}
