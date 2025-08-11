package com.airbnb.clone.backend.user.adapter.out.persistence;

import com.airbnb.clone.backend.user.adapter.out.persistence.entities.AuthorityEntity;
import com.airbnb.clone.backend.user.adapter.out.persistence.entities.UserEntity;
import com.airbnb.clone.backend.user.adapter.out.persistence.repository.UserRepository;
import com.airbnb.clone.backend.user.application.mapper.UserMapper;
import com.airbnb.clone.backend.user.application.port.output.UserRepositoryPort;
import com.airbnb.clone.backend.user.domain.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class JpaUserRepository implements UserRepositoryPort {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public JpaUserRepository(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    private final Logger log = LoggerFactory.getLogger(JpaUserRepository.class);

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
    public Optional<User> findUserByEmail(String email) {
        log.info("Finding user by email: {}", email);
        Optional<UserEntity> entity = userRepository.findByEmail(email);
        return Optional.empty();
    }



    @Override
    public void updateUser(User updatedUserDetails, Integer userId) {
        log.info("Updating user: {}", updatedUserDetails);

    }


}
