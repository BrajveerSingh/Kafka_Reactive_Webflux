package com.study.springbootkafkatutorial.kafka;

import com.study.springbootkafkatutorial.model.User;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class JsonKafkaUserConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonKafkaUserConsumer.class);
    private final ReactiveKafkaConsumerTemplate<String, User> reactiveKafkaConsumerTemplate;


    public JsonKafkaUserConsumer(ReactiveKafkaConsumerTemplate<String, User> reactiveKafkaConsumerTemplate) {
        this.reactiveKafkaConsumerTemplate = reactiveKafkaConsumerTemplate;
    }


    @EventListener(ApplicationStartedEvent.class)
    public Flux<User> startKafkaConsumer() {
        return reactiveKafkaConsumerTemplate
                .receiveAutoAck()
                // .delayElements(Duration.ofSeconds(2L)) // BACKPRESSURE
                .doOnNext(consumerRecord -> LOGGER.info("received key={}, value={} from topic={}, offset={}",
                        consumerRecord.key(),
                        consumerRecord.value(),
                        consumerRecord.topic(),
                        consumerRecord.offset())
                )
                .map(ConsumerRecord<String, User>::value)
                .doOnNext(user -> LOGGER.info("successfully consumed {}={}", String.class.getSimpleName(), user))
                .doOnError(throwable -> LOGGER.error("something bad happened while consuming : {}", throwable.getMessage()));
    }
}
