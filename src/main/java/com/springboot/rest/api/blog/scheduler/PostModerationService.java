package com.springboot.rest.api.blog.scheduler;

import com.springboot.rest.api.blog.repository.PostRepository;
import com.springboot.rest.api.blog.service.InvalidWordService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class PostModerationService {

    private PostRepository postRepository;
    private InvalidWordService invalidWordService;

    // Every 15 seconds using fixed rate
    @Scheduled(fixedRateString = "${blog.moderation.post.fixed-rate}")
    public void moderatePosts() {
        postRepository
                .findByModerationDateNull(Pageable.ofSize(2))
                .forEach(post -> {
                    post.setModerationDate(LocalDateTime.now());

                    invalidWordService.findAllInvalidWordList()
                        .stream()
                        .filter(invalidWord -> List.of(post.getTitle(), post.getContent())
                                .stream()
                                .anyMatch(text -> text.toLowerCase().contains(invalidWord)))
                        .reduce((partialString, element) -> partialString + ", " + element)
                        .ifPresent(invalidWords -> {
                            log.info("Invalid words on post: [{}] - post: {}", invalidWords, post);
                            post.setModerationReason(String.format("Invalid words: [%s]", invalidWords));
                        });

                    postRepository.save(post);
                });
    }

}
