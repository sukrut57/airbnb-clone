package com.airbnb.clone.backend.user.adapter.in.rest;

import com.airbnb.clone.backend.user.adapter.in.rest.dto.UserDto;
import com.airbnb.clone.backend.user.application.port.input.UserSynchronizerUseCase;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class UserResource {

    private final UserSynchronizerUseCase userSynchronizer;
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(UserResource.class);

    public UserResource(UserSynchronizerUseCase userSynchronizer) {
        this.userSynchronizer = userSynchronizer;
    }

    @GetMapping("/user")
    public ResponseEntity<UserDto> getUserDetails(Authentication connectedUser){
        return ResponseEntity.ok(userSynchronizer.getUserDetails(connectedUser));
    }
}
