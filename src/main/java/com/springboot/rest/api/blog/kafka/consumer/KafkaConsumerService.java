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

    @KafkaListener(topics = {"POST"}, clientIdPrefix = "postConsumer", groupId = "postConsumerGroupId")
    public void consumePost(final @Payload String message,
                            final @Header(KafkaHeaders.OFFSET) Integer offset,
                            final @Header(KafkaHeaders.RECEIVED_KEY) String key,
                            final @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
                            final @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                            final @Header(KafkaHeaders.RECEIVED_TIMESTAMP) long ts,
                            final Acknowledgment acknowledgment
    ) throws JsonProcessingException {
        NewRemotePostDto postDto = objectMapper.readValue(message, NewRemotePostDto.class);
        log.info("Post message consumed -> TIMESTAMP={}, offset={}, key={}, partition={}, topic:{}, postDto={}", ts, offset, key, partition, topic, postDto);
        jsonPlaceHolderService.addRemotePosts(postDto);
        acknowledgment.acknowledge();
    }

    @KafkaListener(topics = {"COMMENT"}, clientIdPrefix = "commentConsumer", groupId = "commentConsumerGroupId")
    public void consumeComment(final @Payload String message,
                              final @Header(KafkaHeaders.OFFSET) Integer offset,
                              final @Header(KafkaHeaders.RECEIVED_KEY) String key,
                              final @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
                              final @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                              final @Header(KafkaHeaders.RECEIVED_TIMESTAMP) long ts,
                              final Acknowledgment acknowledgment
    ) throws JsonProcessingException {
        NewRemoteCommentAsyncDto commentDto = objectMapper.readValue(message, NewRemoteCommentAsyncDto.class);
        log.info("Comment message consumed -> TIMESTAMP={}, offset={}, key={}, partition={}, topic:{}, commentDto={}", ts, offset, key, partition, topic, commentDto);
        jsonPlaceHolderService.addRemoteComments(commentDto.getPostId(), commentDto.getNewRemoteCommentDto());
        acknowledgment.acknowledge();
    }

}
