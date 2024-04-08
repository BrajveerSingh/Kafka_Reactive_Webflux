package com.study.springbootkafkatutorial.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Service;

@Service
public class TestTopicKafkaProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestTopicKafkaProducer.class);
    private final ReactiveKafkaProducerTemplate<String, String> reactiveKafkaProducerTemplate;
    @Value("${kafka.test-topic}")
    private String testTopic;

    public TestTopicKafkaProducer(ReactiveKafkaProducerTemplate<String, String> reactiveKafkaProducerTemplate) {
        this.reactiveKafkaProducerTemplate = reactiveKafkaProducerTemplate;
    }

    public void sendMessage(final String message) {
        LOGGER.info("Message sent.  message: {}, topic:{}", message, testTopic);
        reactiveKafkaProducerTemplate.send(testTopic, message)
                .doOnSuccess(senderResult -> LOGGER.info("sent {} offset : {}", message, senderResult.recordMetadata().offset()))
                .subscribe();
    }

    public void sendMessage(final String key, final String message) {
        LOGGER.info("Message sent.  key: {}, message: {}, topic:{}", key, message, testTopic);
        reactiveKafkaProducerTemplate.send(testTopic, key, message)
                .doOnSuccess(senderResult -> LOGGER.info("sent message: {} with key:{} at offset: {}", message, key, senderResult.recordMetadata().offset()))
                .subscribe();
    }
}
