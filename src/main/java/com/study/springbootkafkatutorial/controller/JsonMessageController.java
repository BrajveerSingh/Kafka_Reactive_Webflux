package com.study.springbootkafkatutorial.controller;

import com.study.springbootkafkatutorial.kafka.TestTopicKafkaProducer;
import com.study.springbootkafkatutorial.kafka.TestTopicKafkaUserProducer;
import com.study.springbootkafkatutorial.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/kafka")
public class JsonMessageController {
    private final TestTopicKafkaUserProducer testTopicKafkaUserProducer;

    public JsonMessageController(TestTopicKafkaUserProducer testTopicKafkaUserProducer) {
        this.testTopicKafkaUserProducer = testTopicKafkaUserProducer;
    }

    @PostMapping("/publish")
    public ResponseEntity<String> publishMessage(@RequestBody final User user) {
        testTopicKafkaUserProducer.sendMessage(user);
        return ResponseEntity.ok("Json message published successfully");
    }

}
