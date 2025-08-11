package com.airbnb.clone.backend.user.adapter.in.rest.dto;

public record UserDto(
        String email,
        String firstName,
        String lastName,
        String profilePictureUrl,
        boolean accountEnabled,
        String publicId
) {

}