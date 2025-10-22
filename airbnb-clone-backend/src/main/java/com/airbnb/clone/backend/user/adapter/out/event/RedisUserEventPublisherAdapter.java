package com.airbnb.clone.backend.user.adapter.out.event;

import com.airbnb.clone.backend.user.application.port.output.PublishUserEventPort;
import com.airbnb.clone.backend.user.domain.model.User;

public class RedisUserEventPublisherAdapter implements PublishUserEventPort {

    @Override
    public void publishUserCreatedEvent(User user) {
        //todo
    }
}
