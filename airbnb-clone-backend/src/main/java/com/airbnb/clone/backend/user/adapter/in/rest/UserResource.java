package com.airbnb.clone.backend.user.adapter.in.rest;

import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class UserResource {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(UserResource.class);

    @GetMapping("/users")
    public ResponseEntity<String> getUsers(){
        log.info("Fetching all users");
        return ResponseEntity.ok("Hello World");
    }

    @GetMapping("/users/me")
    public String getMe(){
        return "Hello Me";
    }
}
