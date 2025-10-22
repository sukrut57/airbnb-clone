package com.airbnb.clone.backend.user.adapter.in.rest;

import com.airbnb.clone.backend.user.adapter.in.rest.dto.UserDto;
import com.airbnb.clone.backend.user.application.mapper.UserMapper;
import com.airbnb.clone.backend.user.application.port.input.UserSynchronizerUseCase;
import com.airbnb.clone.backend.user.domain.model.User;
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
    private final UserMapper userMapper;
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(UserResource.class);

    public UserResource(UserSynchronizerUseCase userSynchronizer, UserMapper userMapper) {
        this.userSynchronizer = userSynchronizer;
        this.userMapper = userMapper;
    }

    @GetMapping("/user")
    public ResponseEntity<UserDto> getUserDetails(Authentication connectedUser){
        User user = userSynchronizer.getUserDetails(connectedUser);
        UserDto userDto = userMapper.mapUserDomainToUserDto(user);
        return ResponseEntity.ok(userDto);
    }
}
