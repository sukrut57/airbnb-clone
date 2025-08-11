package com.airbnb.clone.backend.user.adapter.out.persistence.repository;

import com.airbnb.clone.backend.user.adapter.out.persistence.entities.AuthorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<AuthorityEntity,Integer> {
}
