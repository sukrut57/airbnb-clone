package com.airbnb.clone.backend.user.adapter.out.event;

import com.airbnb.clone.backend.user.application.port.output.PublishUserEventPort;
import com.airbnb.clone.backend.user.domain.event.UserCreatedEvent;
import com.airbnb.clone.backend.user.domain.event.UserEventType;
import com.airbnb.clone.backend.user.domain.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class RedisUserEventPublisherAdapter implements PublishUserEventPort {

    private static final Logger log = LoggerFactory.getLogger(RedisUserEventPublisherAdapter.class);
    private final RedisTemplate<String, Object> redisTemplate;

    public RedisUserEventPublisherAdapter(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        System.out.println(redisTemplate.getValueSerializer().getClass());
    }

    @Override
    public void publishUserCreatedEvent(User user) {
        log.info("Publishing UserCreatedEvent for user: {}", user);
        UserCreatedEvent userCreatedEvent = new UserCreatedEvent(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                Instant.now(),
                "Hello"
        );
        redisTemplate.convertAndSend(UserEventType.USER_CREATED.topic(), userCreatedEvent);
    }
}
