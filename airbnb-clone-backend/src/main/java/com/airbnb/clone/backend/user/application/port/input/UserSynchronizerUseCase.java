package com.airbnb.clone.backend.user.application.port.input;

import com.airbnb.clone.backend.user.adapter.in.rest.dto.UserDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;

public interface UserSynchronizerUseCase {
    /**
     * Synchronizes the user with the identity provider (IDP).
     * This method is typically called after a user logs in or when their session is refreshed.
     *
     * @param tokenValue The JWT token representing the user's session.
     */
    void synchronizeWithIdp(Jwt tokenValue);

    UserDto getUserDetails(Authentication connectedUser);

}
