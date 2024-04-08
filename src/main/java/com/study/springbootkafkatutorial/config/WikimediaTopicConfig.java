package com.study.springbootkafkatutorial.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class WikimediaTopicConfig {

    @Value("${kafka.wikimedia-topic}")
    private String wikimediaTopic;
    @Bean
    public NewTopic wikimediaTopic() {
        return TopicBuilder
                .name(wikimediaTopic)
//                .partitions(3)
//                .replicas(1)
                .build();
    }
}
