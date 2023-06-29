package com.springboot.rest.api.blog.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Value("${blog.kafka.post.topic}")
    private String postTopicName;

    @Value("${blog.kafka.comment.topic}")
    private String commentTopicName;

    private final String COMPRESSION_TYPE_CONFIG_VALUE = "zstd";

    @Bean
    public NewTopic postTopic() {
        return TopicBuilder.name(postTopicName)
                .partitions(2)
                .config(TopicConfig.COMPRESSION_TYPE_CONFIG, COMPRESSION_TYPE_CONFIG_VALUE)
                .build();
    }

    @Bean
    public NewTopic commentTopic() {
        return TopicBuilder.name(commentTopicName)
                .partitions(2)
                .config(TopicConfig.COMPRESSION_TYPE_CONFIG, COMPRESSION_TYPE_CONFIG_VALUE)
                .build();
    }

}
