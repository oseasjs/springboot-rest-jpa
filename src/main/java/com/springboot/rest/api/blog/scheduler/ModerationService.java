package com.springboot.rest.api.blog.scheduler;

import com.springboot.rest.api.blog.repository.CommentRepository;
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
public class ModerationService {

    private PostRepository postRepository;
    private CommentRepository commentRepository;
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

    // Every 20 seconds using chron expression
    @Scheduled(cron = "${blog.moderation.comment.cron}")
    public void moderateComments() {
        commentRepository
                .findByModerationDateNull(Pageable.ofSize(2))
                .forEach(comment -> {
                    comment.setModerationDate(LocalDateTime.now());

                    invalidWordService.findAllInvalidWordList()
                        .stream()
                        .filter(invalidWord -> comment.getContent().toLowerCase().contains(invalidWord))
                        .reduce((partialString, element) -> partialString + ", " + element)
                        .ifPresent(invalidWords -> {
                            log.info("Invalid words on comment: [{}] - comment: {}", invalidWords, comment);
                            comment.setModerationReason(String.format("Invalid words: [%s]", invalidWords));
                        });

                    commentRepository.save(comment);
                });
    }

}
