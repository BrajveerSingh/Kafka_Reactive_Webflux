package com.study.springbootkafkatutorial.events;

import com.launchdarkly.eventsource.MessageEvent;
import com.launchdarkly.eventsource.background.BackgroundEventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;

public class WikimediaRecentChangeHandler implements BackgroundEventHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(WikimediaRecentChangeHandler.class);
    private final ReactiveKafkaProducerTemplate<String, String> reactiveKafkaProducerTemplate;
    private final String wikimediaTopic;

    public WikimediaRecentChangeHandler(final ReactiveKafkaProducerTemplate<String, String> reactiveKafkaProducerTemplate,
                                        final String wikimediaTopic) {
        this.reactiveKafkaProducerTemplate = reactiveKafkaProducerTemplate;
        this.wikimediaTopic = wikimediaTopic;
    }

    @Override
    public void onOpen() throws Exception {

    }

    @Override
    public void onClosed() throws Exception {

    }

    @Override
    public void onMessage(String s, MessageEvent messageEvent) throws Exception {
        LOGGER.info("received message: {}", messageEvent.getData());
        reactiveKafkaProducerTemplate.send(wikimediaTopic, messageEvent.getData())
                .doOnSuccess(senderResult -> LOGGER.info("sent message: {} at offset: {}", messageEvent.getData(), senderResult.recordMetadata().offset()))
                .subscribe();
    }

    @Override
    public void onComment(String s) throws Exception {

    }

    @Override
    public void onError(Throwable throwable) {

    }
}
