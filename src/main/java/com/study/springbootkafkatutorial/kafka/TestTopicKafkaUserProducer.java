package com.study.springbootkafkatutorial.kafka;

import com.study.springbootkafkatutorial.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class TestTopicKafkaUserProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestTopicKafkaUserProducer.class);
    private final ReactiveKafkaProducerTemplate<String, User> reactiveKafkaProducerTemplate;
    @Value("${kafka.test-topic}")
    private String testTopic;

    public TestTopicKafkaUserProducer(ReactiveKafkaProducerTemplate<String, User> reactiveKafkaProducerTemplate) {
        this.reactiveKafkaProducerTemplate = reactiveKafkaProducerTemplate;
    }

    public void sendMessage(final User user) {
        String key = String.valueOf(user.getId());
        LOGGER.info("Message sent.  key: {}, message: {}, topic:{}", key, user, testTopic);
//        Message<User> message = MessageBuilder
//                .withPayload(user)
//                .setHeader(KafkaHeaders.KEY, key)
//                .setHeader(KafkaHeaders.TOPIC, testTopic)
//                .build();
        reactiveKafkaProducerTemplate.send(testTopic, key, user)
                .doOnSuccess(senderResult -> LOGGER.info("sent message: {} with key:{} at offset: {}", user, key, senderResult.recordMetadata().offset()))
                .subscribe();
    }

//    public void sendMessage(final User user) {
//        LOGGER.info("Message sent. message: {}, topic:{}", user, testTopic);
////        Message<User> message = MessageBuilder
////                .withPayload(user)
////                .setHeader(KafkaHeaders.TOPIC, testTopic)
////                .build();
//        reactiveKafkaProducerTemplate.send(testTopic, user)
//                .doOnSuccess(senderResult -> LOGGER.info("sent message: {} at offset: {}", user,  senderResult.recordMetadata().offset()))
//                .subscribe();
//    }
}
