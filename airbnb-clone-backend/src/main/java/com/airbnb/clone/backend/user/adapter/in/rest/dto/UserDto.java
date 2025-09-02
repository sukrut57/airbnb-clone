package com.airbnb.clone.backend.user.adapter.in.rest.dto;

import java.util.Set;

public record UserDto(
        String email,
        String firstName,
        String lastName,
        String profilePictureUrl,
        boolean accountEnabled,
        String publicId,
        Set<AuthorityDto> authorities
) {

}