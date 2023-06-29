package com.springboot.rest.api.blog.kafka.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.rest.api.blog.enums.KafkaTopicEnum;
import com.springboot.rest.api.blog.kafka.dto.NewRemoteCommentAsyncDto;
import com.springboot.rest.api.blog.kafka.dto.NewRemotePostAsyncDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Service
@AllArgsConstructor
@Slf4j
public class KafkaProducerService {

    private KafkaTemplate<String, Object> kafkaTemplateObj;
    private ObjectMapper objectMapper;
    private final ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);

    @Async
    public void sendPost(final KafkaTopicEnum topicEnum, final NewRemotePostAsyncDto post) throws Exception {
        for (int i = 0; i < post.getPostDto().getLimit(); i++) {
            log.info("Producing message -> topic={}, postDto={}", topicEnum.name(), objectMapper.writeValueAsString(post));
            executorService.submit(() -> {
                kafkaTemplateObj.send(topicEnum.name(), post);
            });
        }
    }

    @Async
    public void sendComment(KafkaTopicEnum topicEnum, NewRemoteCommentAsyncDto comment) throws JsonProcessingException {

        for (int i = 0; i < comment.getCommentDto().getLimit(); i++) {
            log.info("Producing message -> topic={}, key={}, commentDto={}", topicEnum.name(), comment.getPostId(), objectMapper.writeValueAsString(comment));
            executorService.submit(() -> {
                kafkaTemplateObj.send(topicEnum.name(), comment.getPostId().toString(), comment);
            });
        }
    }

}
