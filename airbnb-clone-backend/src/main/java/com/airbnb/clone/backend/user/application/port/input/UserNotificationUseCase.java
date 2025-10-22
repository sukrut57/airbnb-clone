package com.airbnb.clone.backend.user.application.port.input;

import com.airbnb.clone.backend.user.domain.model.User;

public interface UserNotificationUseCase {
    void notifyUserCreated(User user);
}
