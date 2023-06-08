package com.springboot.rest.api.blog.service;

import com.springboot.rest.api.blog.repository.InvalidWordRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class InvalidWordService {

    private InvalidWordRepository invalidWordRepository;

    @Cacheable("invalidWords")
    public List<String> findAllInvalidWordList() {
        return invalidWordRepository
                .findAll()
                .stream()
                .map(invalidWord -> invalidWord.getWord())
                .toList();
    }

}
