package com.airbnb.clone.backend.user.application.port.output;

import com.airbnb.clone.backend.user.adapter.out.persistence.entities.UserEntity;
import com.airbnb.clone.backend.user.domain.model.User;

import java.util.Optional;

public interface UserRepositoryPort {

    void saveUser(User user);

    Optional<UserEntity> findUserByEmail(String email);

    void updateUser(User updateUserDetails, UserEntity userEntity);

}
