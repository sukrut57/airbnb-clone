package com.airbnb.clone.backend.user.application.service;

import com.airbnb.clone.backend.user.application.port.input.UserNotificationUseCase;
import com.airbnb.clone.backend.user.domain.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserNotificationService implements UserNotificationUseCase {
    @Override
    public void notifyUserCreated(User user) {

    }
}
