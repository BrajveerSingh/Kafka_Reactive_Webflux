package com.study.springbootkafkatutorial.controller;

import com.study.springbootkafkatutorial.kafka.TestTopicKafkaProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/kafka")
public class MessageController {
    private final TestTopicKafkaProducer producer;

    public MessageController(TestTopicKafkaProducer producer) {
        this.producer = producer;
    }

    @GetMapping("/publish")
    public ResponseEntity<String> publishMessage(@RequestParam("message") final String message) {
        producer.sendMessage(message);
        return ResponseEntity.ok("Message published successfully");
    }

    @GetMapping("/publish/key")
    public ResponseEntity<String> publishMessage(@RequestParam("key") final String key, @RequestParam("message") final String message) {
        producer.sendMessage(key, message);
        return ResponseEntity.ok("Message published successfully for the key: " + key);
    }
}
