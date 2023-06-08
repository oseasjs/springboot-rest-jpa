package com.springboot.rest.api.blog.kafka.producer;

import com.springboot.rest.api.blog.enums.KafkaTopicEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class KafkaProducerService {

    private KafkaTemplate<String, String> kafkaTemplate;


    public void sendMessage(KafkaTopicEnum topicEnum, String key, String message) {
        log.info("Producing message -> topic={}, key={}, message={}", topicEnum.name(), key, message);
        kafkaTemplate.send(topicEnum.name(), key, message);
    }

}
