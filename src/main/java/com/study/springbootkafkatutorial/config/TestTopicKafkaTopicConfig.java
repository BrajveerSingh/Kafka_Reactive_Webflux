package com.study.springbootkafkatutorial.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class TestTopicKafkaTopicConfig {

    @Value("${kafka.test-topic}")
    private String testTopic;
    @Bean
    public NewTopic testTopic() {
        return TopicBuilder.name(testTopic)
//                .partitions(3)
//                .replicas(3)
                .build();
    }
}
