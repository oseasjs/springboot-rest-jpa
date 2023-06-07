package com.springboot.rest.api.blog.scheduler;

import com.springboot.rest.api.blog.model.Post;
import com.springboot.rest.api.blog.repository.CommentRepository;
import com.springboot.rest.api.blog.repository.PostRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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

    // Every 15 seconds
    @Scheduled(fixedRateString = "15000")
    public void moderatePosts() {
        postRepository
                .findByModerationDateNull(Pageable.ofSize(2))
                .forEach(post -> {
                    post.setModerationDate(LocalDateTime.now());
                    getInvalidWordList()
                        .stream()
                        .filter(invalidWord -> post.getContent().toLowerCase().contains(invalidWord) || post.getContent().toLowerCase().contains(invalidWord))
                        .findAny()
                        .ifPresent(invalidWord -> {
                            log.info("Post contains an invalid word - content: {} - invalidWord: {}", post, invalidWord);
                            post.setModerationReason(String.format("Post content contains an invalid word: [%s]", invalidWord));
                        });
                    postRepository.save(post);
                });
    }

    // Every 20 seconds
    @Scheduled(cron = "0/20 * * * * *")
    public void moderateComments() {
        commentRepository
                .findByModerationDateNull(Pageable.ofSize(2))
                .forEach(comment -> {
                    comment.setModerationDate(LocalDateTime.now());
                    getInvalidWordList()
                            .stream()
                            .filter(invalidWord -> comment.getContent().toLowerCase().contains(invalidWord))
                            .findAny()
                            .ifPresent(invalidWord -> {
                                log.info("Comment content contains an invalid word - title: {} - invalidWord: {}", comment.getContent(), invalidWord);
                                comment.setModerationReason(String.format("Comment content contains an invalid word: [%s]", invalidWord));
                            });
                    commentRepository.save(comment);
                });
    }



    private List<String> getInvalidWordList() {
        return List.of("dolor", "odio");
    }

    private String validatePost(Post post, String invalidWord) {
        StringBuffer validationMessage = new StringBuffer();

        if (post.getTitle().toLowerCase().contains(invalidWord))
            validationMessage.append("Title");

        if (post.getContent().toLowerCase().contains(invalidWord))
            validationMessage.append("");

        return StringUtils.defaultIfBlank(validationMessage.toString(), null);
    }

}
