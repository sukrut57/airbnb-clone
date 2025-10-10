package com.airbnb.clone.backend.user.adapter.out.persistence;

import com.airbnb.clone.backend.user.adapter.out.persistence.entities.AuthorityEntity;
import com.airbnb.clone.backend.user.adapter.out.persistence.entities.UserEntity;
import com.airbnb.clone.backend.user.adapter.out.persistence.repository.AuthorityRepository;
import com.airbnb.clone.backend.user.adapter.out.persistence.repository.UserRepository;
import com.airbnb.clone.backend.user.application.mapper.UserMapper;
import com.airbnb.clone.backend.user.application.port.output.UserRepositoryPort;
import com.airbnb.clone.backend.user.domain.model.User;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * JPA-based persistence adapter implementing `UserRepositoryPort`.
 *
 * ⚙️ Purpose:
 *  - Encapsulates all database interaction logic.
 *  - Ensures roles (authorities) are reused from DB — not re-created.
 *  - Keeps transactional integrity and safe updates for user data.
 */

@Service
public class JpaUserRepository implements UserRepositoryPort {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthorityRepository authorityRepository;

    public JpaUserRepository(UserRepository userRepository, UserMapper userMapper, AuthorityRepository authorityRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.authorityRepository = authorityRepository;
    }

    private final Logger log = LoggerFactory.getLogger(JpaUserRepository.class);

    @Transactional
    @Override
    public void saveUser(User user) {
        UserEntity userEntity = userMapper.mapUserDomainToUserEntity(user);
        Set<AuthorityEntity> authorityEntities = user.getAuthorities().stream()
                        .map(userMapper::mapAuthorityDomainToAuthorityEntity).collect(Collectors.toSet());
        userEntity.setAuthorities(authorityEntities);

        log.info("Saving user: {}", user);
        userRepository.saveAndFlush(userEntity);
    }

    @Override
    public Optional<UserEntity> findUserByEmail(String email) {
        log.info("Finding user by email: {}", email);
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<UserEntity> findUserByEmailWithAuthorities(String email) {
        log.info("retrieving user details: {}", email);
        return userRepository.findByEmailWithAuthorities(email);
    }


    @Transactional
    @Override
    public void updateUser(User updatedUserDetails, UserEntity userEntity) {
        log.info("Updating user: {}", updatedUserDetails);
        // Map updatedUserDetails to userEntity
        // Copy simple fields into the managed entity

        userMapper.updateUserEntityFromDomain(updatedUserDetails, userEntity);

        // Handle authorities separately (so roles can be created on-the-fly)
        Set<AuthorityEntity> authorityEntities = updatedUserDetails.getAuthorities().stream()
                .map(userMapper::mapAuthorityDomainToAuthorityEntity)
                .map(authority -> authorityRepository.findByName(authority.getName())
                        .orElseGet(() -> authorityRepository.save(authority)))
                .collect(Collectors.toSet());

        userEntity.setAuthorities(authorityEntities);

        userRepository.saveAndFlush(userEntity);

    }



}
