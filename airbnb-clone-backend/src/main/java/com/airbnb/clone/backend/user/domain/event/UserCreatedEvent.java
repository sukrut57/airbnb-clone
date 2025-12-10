package com.airbnb.clone.backend.user.domain.event;

import java.time.Instant;

public record UserCreatedEvent (
    Integer id,
    String email,
    String firstName,
    String lastName,
    Instant createdAt,
    String welcomeMessage
){}
