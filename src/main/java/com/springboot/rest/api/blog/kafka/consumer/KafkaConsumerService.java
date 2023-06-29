package com.springboot.rest.api.blog.kafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.rest.api.blog.controller.dto.NewRemotePostDto;
import com.springboot.rest.api.blog.feign.client.JsonPlaceHolderService;
import com.springboot.rest.api.blog.kafka.dto.NewRemoteCommentAsyncDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class KafkaConsumerService {

    private JsonPlaceHolderService jsonPlaceHolderService;
    private ObjectMapper objectMapper;

    @KafkaListener(topics = {"${blog.kafka.post.topic}"},
            clientIdPrefix = "${blog.kafka.post.prefix}",
            groupId = "${blog.kafka.post.group-id}")
    public void consumePost(final @Payload String message,
                            final @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                            final @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
                            final @Header(KafkaHeaders.RECEIVED_KEY) String key,
                            final @Header(KafkaHeaders.OFFSET) Integer offset,
                            final @Header(KafkaHeaders.RECEIVED_TIMESTAMP) long ts,
                            final Acknowledgment acknowledgment
    ) throws JsonProcessingException {
        log.info("Post message consumed -> topic:{}, partition={}, key={}, offset={}, TIMESTAMP={}, postDto={}",
                topic, partition, key, offset, ts, message);
        NewRemotePostDto postDto = objectMapper.readValue(message, NewRemotePostDto.class);
        jsonPlaceHolderService.addRemotePosts(postDto);
        acknowledgment.acknowledge();
    }

    @KafkaListener(topics = {"${blog.kafka.comment.topic}"},
            clientIdPrefix = "${blog.kafka.comment.prefix}",
            groupId = "${blog.kafka.comment.group-id}")
    public void consumeComment(final @Payload String message,
                               final @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                               final @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
                               final @Header(KafkaHeaders.RECEIVED_KEY) String key,
                               final @Header(KafkaHeaders.OFFSET) Integer offset,
                               final @Header(KafkaHeaders.RECEIVED_TIMESTAMP) long ts,
                               final Acknowledgment acknowledgment
    ) throws JsonProcessingException {
        log.info("Comment message consumed -> topic:{}, partition={}, key={}, offset={}, TIMESTAMP={}, commentDto={}",
                topic, partition, key, offset, ts, message);
        NewRemoteCommentAsyncDto commentDto = objectMapper.readValue(message, NewRemoteCommentAsyncDto.class);
        jsonPlaceHolderService.addRemoteComments(commentDto.getPostId(), commentDto.getNewRemoteCommentDto());
        acknowledgment.acknowledge();
    }

}
