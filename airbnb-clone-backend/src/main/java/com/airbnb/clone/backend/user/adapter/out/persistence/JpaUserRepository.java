package com.airbnb.clone.backend.user.adapter.out.persistence;

import com.airbnb.clone.backend.shared.exception.UserSynchronizationException;
import com.airbnb.clone.backend.user.adapter.out.persistence.entities.AuthorityEntity;
import com.airbnb.clone.backend.user.adapter.out.persistence.entities.UserEntity;
import com.airbnb.clone.backend.user.adapter.out.persistence.repository.AuthorityRepository;
import com.airbnb.clone.backend.user.adapter.out.persistence.repository.UserRepository;
import com.airbnb.clone.backend.user.application.mapper.UserMapper;
import com.airbnb.clone.backend.user.application.port.output.UserRepositoryPort;
import com.airbnb.clone.backend.user.domain.model.Authority;
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

//        //extract the role names from the Authority domain object
//        Set<AuthorityEntity> authorityEntities = user.getAuthorities().stream()
//                        .map(userMapper::mapAuthorityDomainToAuthorityEntity).collect(Collectors.toSet());
//        userEntity.setAuthorities(authorityEntities);

        //extract roles from User domain
        Set<String> extractRoles = user.getAuthorities().stream()
                        .map(Authority::getName)
                                .collect(Collectors.toSet());

       List<AuthorityEntity> authorityEntities = authorityRepository.findAllByNameIn(extractRoles);
       validateAuthoritiesExists(authorityEntities,extractRoles);

       log.info("Saving user: {}", user);
       userRepository.saveAndFlush(userEntity);
    }

    private void validateAuthoritiesExists(List<AuthorityEntity> authorityEntities, Set<String> extractRoles) {
      //extract roles from db
        Set<String> dbRoles = authorityEntities.stream().map(AuthorityEntity::getName).collect(Collectors.toSet());

        //find missing roles
        Set<String> missingRoles = new HashSet<>(extractRoles);
        missingRoles.removeAll(dbRoles);

        if(!missingRoles.isEmpty()){
            throw new UserSynchronizationException("The following roles do not exist in the database: " + missingRoles.toString());
        }
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
        // Map updatedUserDetails to userEntity
        // Copy simple fields into the managed entity

        userMapper.updateUserEntityFromDomain(updatedUserDetails, userEntity);

        //extract roles from the domain model
        Set<String> extractRoles = updatedUserDetails.getAuthorities().stream()
                .map(Authority::getName)
                .collect(Collectors.toSet());

        //retrieve roles from the database
        List<AuthorityEntity> authorityEntities = authorityRepository.findAllByNameIn(extractRoles);

        //validate all roles exists in db
        validateAuthoritiesExists(authorityEntities,extractRoles);

        //update user authorities with the new ones

        Set<AuthorityEntity> authoritiesToUpdate = new HashSet<>();
        for(String role:extractRoles){
            Optional<AuthorityEntity> authority = authorityRepository.findByName(role);
            if(authority.isPresent()){
                authoritiesToUpdate.add(authority.get());
            }
            else{
                throw new UserSynchronizationException("The following role does not exist in the database: " + role);
            }
        }
        if(checkIfUserInfoUpdated(updatedUserDetails,userEntity)){
            userEntity.setAuthorities(authoritiesToUpdate);
            log.info("Updating user: {}", updatedUserDetails);
            userRepository.saveAndFlush(userEntity);
        }
        else{
            log.info("User details not updated. Skipping update. User: {}", updatedUserDetails);
        }
//        // Handle authorities separately (so roles can be created on-the-fly)
//        Set<AuthorityEntity> authorityEntities = updatedUserDetails.getAuthorities().stream()
//                .map(userMapper::mapAuthorityDomainToAuthorityEntity)
//                .map(authority -> authorityRepository.findByName(authority.getName())
//                        .orElseGet(() -> authorityRepository.save(authority)))
//                .collect(Collectors.toSet());

    }

    private boolean checkIfUserInfoUpdated(User updatedUserDetails, UserEntity userEntity) {

        boolean nameChanged = !Objects.equals(updatedUserDetails.getFirstName(), userEntity.getFirstName())
                || !Objects.equals(updatedUserDetails.getLastName(), userEntity.getLastName());

        boolean emailChanged = !Objects.equals(updatedUserDetails.getEmail(), userEntity.getEmail());

        boolean authoritiesChanged = updatedUserDetails.getAuthorities() != null
                && !updatedUserDetails.getAuthorities().isEmpty()
                && !updatedUserDetails.getAuthorities().stream()
                .map(Authority::getName)
                .collect(Collectors.toSet())
                .equals(userEntity.getAuthorities().stream()
                        .map(AuthorityEntity::getName)
                        .collect(Collectors.toSet()));

        return nameChanged || emailChanged || authoritiesChanged;
    }


}
