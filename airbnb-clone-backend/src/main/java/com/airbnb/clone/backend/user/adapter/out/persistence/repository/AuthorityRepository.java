package com.airbnb.clone.backend.user.adapter.out.persistence.repository;

import com.airbnb.clone.backend.user.adapter.out.persistence.entities.AuthorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface AuthorityRepository extends JpaRepository<AuthorityEntity,String> {

    Optional<AuthorityEntity> findByName(String name);

}
