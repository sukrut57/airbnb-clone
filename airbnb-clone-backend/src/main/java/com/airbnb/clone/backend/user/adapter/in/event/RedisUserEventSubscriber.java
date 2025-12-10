package com.airbnb.clone.backend.user.adapter.in.event;

import com.airbnb.clone.backend.user.domain.event.UserCreatedEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Component
public class RedisUserEventSubscriber implements MessageListener {
    Logger log = org.slf4j.LoggerFactory.getLogger(RedisUserEventSubscriber.class);


    private final ObjectMapper mapper;

    public RedisUserEventSubscriber(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            // Raw JSON payload from Redis pub/sub
            String json = new String(message.getBody());
            log.info("RAW JSON RECEIVED: {}", json);
            // Convert JSON -> event
            UserCreatedEvent event = mapper.readValue(json, UserCreatedEvent.class);
            log.info("Deserialized UserCreatedEvent: {}", event);

            //todo send email to the new user

        } catch (Exception e) {
            log.error("Failed to process Redis event", e);
        }
    }
}
