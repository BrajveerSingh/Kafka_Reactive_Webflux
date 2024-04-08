package com.study.springbootkafkatutorial.kafka;

import com.launchdarkly.eventsource.EventSource;
import com.launchdarkly.eventsource.StreamException;
import com.launchdarkly.eventsource.background.BackgroundEventHandler;
import com.launchdarkly.eventsource.background.BackgroundEventSource;
import com.study.springbootkafkatutorial.events.WikimediaRecentChangeHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.concurrent.TimeUnit;

@Service
public class WikimediaRecentChangesProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(WikimediaRecentChangesProducer.class);
    private final ReactiveKafkaProducerTemplate<String, String> reactiveKafkaProducerTemplate;
    @Value("${kafka.wikimedia-topic}")
    private String wikimediaTopic;

    public WikimediaRecentChangesProducer(ReactiveKafkaProducerTemplate<String, String> reactiveKafkaProducerTemplate) {
        this.reactiveKafkaProducerTemplate = reactiveKafkaProducerTemplate;
    }

    public void sendMessage() throws InterruptedException {

        //TODO: Read real time data from event source and send it to Kafka topic

        BackgroundEventHandler eventHandler = new WikimediaRecentChangeHandler(reactiveKafkaProducerTemplate, wikimediaTopic);
        String url = "https://stream.wikimedia.org/v2/stream/recentchange";
        EventSource.Builder  builder = new EventSource.Builder(URI.create(url));
//        BackgroundEventSource.Builder eventSource = new BackgroundEventSource.Builder(eventHandler, builder);
//        try (BackgroundEventSource backgroundEventSource = eventSource.build()) {
//            backgroundEventSource.start();
//        } catch (Exception e) {
//            LOGGER.error("Error while reading from event source: {}", e.getMessage());
//        }
        try {
            builder.build().start();
        } catch (StreamException e) {
            throw new RuntimeException(e);
        }

        TimeUnit.MINUTES.sleep(10);
//        reactiveKafkaProducerTemplate.send(testTopic, key, user)
//                .doOnSuccess(senderResult -> LOGGER.info("sent message: {} with key:{} at offset: {}", user, key, senderResult.recordMetadata().offset()))
//                .subscribe();
    }

}
