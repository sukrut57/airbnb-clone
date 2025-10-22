package com.airbnb.clone.backend.user.application.port.output;

import com.airbnb.clone.backend.user.domain.model.User;

public interface PublishUserEventPort {
    void publishUserCreatedEvent(User user);
}
