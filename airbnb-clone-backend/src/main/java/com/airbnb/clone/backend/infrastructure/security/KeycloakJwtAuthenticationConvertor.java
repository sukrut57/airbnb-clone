package com.airbnb.clone.backend.infrastructure.security;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class KeycloakJwtAuthenticationConvertor implements Converter<Jwt, AbstractAuthenticationToken> {

    private static final Logger log = LoggerFactory.getLogger(KeycloakJwtAuthenticationConvertor.class);
    private final String clientId;

    public KeycloakJwtAuthenticationConvertor(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public AbstractAuthenticationToken convert(@NonNull Jwt source) {
        var jwtAuthorities = new JwtGrantedAuthoritiesConverter().convert(source);
        var keyCloakAuthorities = extractResourceRoles(source);

        return new JwtAuthenticationToken(source,
                Stream.concat(jwtAuthorities.stream(), keyCloakAuthorities.stream())
                        .collect(Collectors.toSet()));
    }

    private Collection<? extends GrantedAuthority> extractResourceRoles(Jwt source) {
        var resourceAccess = new HashMap<>(source.getClaim("resource_access"));

        var clientAccess = (Map<String, Object>) resourceAccess.get(clientId);
        var azp = source.getClaimAsString("azp");

        if(azp != null && !azp.equals(clientId)){
            log.error("azp does not match expected client");
            throw new OAuth2AuthenticationException(new OAuth2Error("invalid_token", "Token azp does not match expected client", null));
        }
        if (clientAccess == null) {
            return Collections.emptyList();
        }
        var roles = (List<String>) clientAccess.get("roles");

        return roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.replace("-","_")))
                .collect(Collectors.toSet());
    }
}
