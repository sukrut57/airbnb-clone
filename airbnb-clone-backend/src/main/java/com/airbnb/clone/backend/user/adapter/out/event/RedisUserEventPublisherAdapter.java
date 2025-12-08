package com.airbnb.clone.backend.user.adapter.out.event;

import com.airbnb.clone.backend.user.application.port.output.PublishUserEventPort;
import com.airbnb.clone.backend.user.domain.model.User;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisUserEventPublisherAdapter implements PublishUserEventPort {

    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void publishUserCreatedEvent(User user) {
        //todo
    }
}
