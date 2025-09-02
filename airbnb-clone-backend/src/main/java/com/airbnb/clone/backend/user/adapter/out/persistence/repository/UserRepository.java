package com.airbnb.clone.backend.user.adapter.out.persistence.repository;

import com.airbnb.clone.backend.user.adapter.out.persistence.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Integer> {

    @Query("SELECT u FROM UserEntity u WHERE u.email = :email")
    Optional<UserEntity> findByEmail(@Param("email") String email);

    @Query("SELECT u FROM UserEntity u LEFT JOIN FETCH u.authorities WHERE u.email = :email")
    Optional<UserEntity> findByEmailWithAuthorities(@Param("email") String email);
}
