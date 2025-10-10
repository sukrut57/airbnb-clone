package com.airbnb.clone.backend.user.adapter.out.persistence.repository;

import com.airbnb.clone.backend.user.adapter.out.persistence.entities.AuthorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;


public interface AuthorityRepository extends JpaRepository<AuthorityEntity,String> {

    Optional<AuthorityEntity> findByName(String name);

    @Query("SELECT a FROM AuthorityEntity a WHERE a.name IN :names")
    List<AuthorityEntity> findAllByNameIn(@Param("names") Set<String> names);

}
