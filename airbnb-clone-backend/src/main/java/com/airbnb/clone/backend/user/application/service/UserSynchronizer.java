package com.airbnb.clone.backend.user.application.service;

import com.airbnb.clone.backend.shared.exception.UserSynchronizationException;
import com.airbnb.clone.backend.user.adapter.in.rest.dto.AuthorityDto;
import com.airbnb.clone.backend.user.adapter.in.rest.dto.UserDto;
import com.airbnb.clone.backend.user.adapter.out.persistence.entities.AuthorityEntity;
import com.airbnb.clone.backend.user.adapter.out.persistence.entities.UserEntity;
import com.airbnb.clone.backend.user.application.mapper.UserMapper;
import com.airbnb.clone.backend.user.application.port.input.UserSynchronizerUseCase;
import com.airbnb.clone.backend.user.application.port.output.UserRepositoryPort;
import com.airbnb.clone.backend.user.domain.model.Authority;
import com.airbnb.clone.backend.user.domain.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class UserSynchronizer implements UserSynchronizerUseCase {


    private static final Logger log = LoggerFactory.getLogger(UserSynchronizer.class);

    private final UserRepositoryPort userRepositoryPort;
    private final UserMapper userMapper;

    public UserSynchronizer(UserRepositoryPort userRepositoryPort, UserMapper userMapper) {
        this.userRepositoryPort = userRepositoryPort;
        this.userMapper = userMapper;
    }

    public void synchronizeWithIdp(Jwt tokenValue) {
        //extract user information from the token
        getUserEmail(tokenValue).ifPresentOrElse(userEmail -> {
            //retrieve user from the database
            Optional<UserEntity> user = retrieveUserByEmail(userEmail);
            if(user.isPresent()){
                //update the user with the latest information from the token

                try{
                    User updatedUserEntityDetails = retrieveUserDetailsFromToken(tokenValue);
                    userRepositoryPort.updateUser(updatedUserEntityDetails, user.get());
                }
                catch (Exception e){
                    throw new UserSynchronizationException("Error updating user details", e);
                }
            }
            else{
                try{
                    User newUserEntityDetails = retrieveUserDetailsFromToken(tokenValue);
                    userRepositoryPort.saveUser(newUserEntityDetails);
                }
                catch (Exception e){
                    throw new UserSynchronizationException("Error creating user details", e);
                }
            }
        }, () -> {
            throw new UserSynchronizationException("User email not found in token");
        });
    }


    private Optional<UserEntity> retrieveUserByEmail(String email){
        return userRepositoryPort.findUserByEmail(email);
    }

    private Optional<String> getUserEmail(Jwt token) {
        return Optional.ofNullable(token.getClaimAsString("email"));
//        Map<String, Object> claims = token.getClaims();
//        if(claims.containsKey("email")){
//            return Optional.of(claims.get("email").toString());
//        }
//        else {
//            return Optional.empty();
//        }
    }

    private User retrieveUserDetailsFromToken(Jwt token) {

        User user = new User();

        String email = token.getClaim("email");
        if (email != null) {
            user.setEmail(email);
        } else {
            log.warn("User email not found in token claims");
        }

        String firstName = token.getClaim("given_name");
        if (firstName == null) {
            String fullName = token.getClaim("name");
            if (fullName != null && !fullName.trim().isEmpty()) {
                firstName = fullName.trim().split(" ")[0];
            }
        }
        if (firstName != null) {
            user.setFirstName(firstName);
        } else {
            log.warn("User first name not found in token claims");
        }

        String lastName = token.getClaim("family_name");
        if (lastName != null) {
            user.setLastName(lastName);
        } else {
            log.warn("User last name not found in token claims");
        }

        String sub = token.getClaim("sub");
        if (sub != null) {
            try {
                UUID.fromString(sub);
                user.setPublicId(sub);
            } catch (IllegalArgumentException e) {
                log.error("Invalid UUID format in 'sub' claim", e);
            }
        } else {
            log.warn("User public ID not found in token claims");
        }

        user.setAccountEnabled(true);

        var resourceAccess = new HashMap<>(token.getClaim("resource_access"));
        Set<Authority> authorities = new HashSet<>();
        var clientAccess = (Map<String, Object>) resourceAccess.get("airbnb_client-app2app");
        if(clientAccess!=null && clientAccess.containsKey("roles")){
            var roles = (List<String>) clientAccess.get("roles");
            if(roles!=null && !roles.isEmpty()){
                for(String role: roles){
                    Authority authority = new Authority();
                    authority.setName(role.replace("-", "_"));
                    authorities.add(authority);
                }
            }
        } else {
            log.warn("User roles not found in token claims");
        }
        user.setAuthorities(authorities);

        printUserDetails(user);
        return user;
    }

    private void printUserDetails(User user) {
        log.info("User Details: Email: {}, First Name: {}, Last Name: {}, Public ID: {}",
                user.getEmail(), user.getFirstName(), user.getLastName(), user.getPublicId());
    }


    @Override
    public UserDto getUserDetails(Authentication authentication){
        if(!(authentication instanceof JwtAuthenticationToken)){
            throw new UserSynchronizationException("Authentication is not a JWT token");
        }
        JwtAuthenticationToken jwtAuthToken = (JwtAuthenticationToken) authentication;
        String email = jwtAuthToken.getToken().getClaimAsString("email");
        if(email==null || email.isEmpty()){
            throw new UserSynchronizationException("User email not found in token");
        }
        Optional<UserEntity> user = userRepositoryPort.findUserByEmailWithAuthorities(email);
        if(user.isEmpty()){
            throw new UserSynchronizationException("User not found in the database");
        }
        UserDto userDto = userMapper.mapUserEntityToUserDto(user.get());
        return userDto;
    }

}
