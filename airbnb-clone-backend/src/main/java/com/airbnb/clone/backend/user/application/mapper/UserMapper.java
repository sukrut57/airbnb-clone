package com.airbnb.clone.backend.user.application.mapper;

import com.airbnb.clone.backend.user.adapter.in.rest.dto.AuthorityDto;
import com.airbnb.clone.backend.user.adapter.in.rest.dto.UserDto;
import com.airbnb.clone.backend.user.adapter.out.persistence.entities.AuthorityEntity;
import com.airbnb.clone.backend.user.adapter.out.persistence.entities.UserEntity;
import com.airbnb.clone.backend.user.domain.model.Authority;
import com.airbnb.clone.backend.user.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Converts between domain `User` objects and persistence `UserEntity` JPA entities.
 *
 * ⚙️ Responsibilities:
 *  - Provides directional mappings (Domain ↔ Entity ↔ DTO).
 *  - Avoids persistence logic — purely structural translation.
 *  - Leaves authority and audit management to repository layer.
 */
@Mapper(componentModel = "spring")
public interface UserMapper {


    // Map User domain -> UserEntity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "publicId", source = "publicId", qualifiedByName = "stringToUUID")
    @Mapping(target = "authorities", source = "authorities")
    UserEntity mapUserDomainToUserEntity(User user);

    @Mapping(target = "publicId", qualifiedByName = "uuidToString")
    @Mapping(target = "authorities", source = "authorities")
    UserDto mapUserEntityToUserDto(UserEntity user);

    // Map AuthorityEntity -> AuthorityDto
    @Mapping(target = "name", source = "name")
    AuthorityDto mapAuthorityEntityToAuthorityDto(AuthorityEntity authorityEntity);

    // Map Authority domain -> AuthorityEntity
    @Mapping(target = "name", source = "name")
    AuthorityEntity mapAuthorityDomainToAuthorityEntity(Authority authority);

    // Map a set of Authorities (domain) to AuthorityEntities
    default Set<AuthorityEntity> mapAuthorities(Set<Authority> authorities) {
        if (authorities == null) return null;
        return authorities.stream()
                .map(this::mapAuthorityDomainToAuthorityEntity)
                .collect(Collectors.toSet());
    }


    /**
     * Updates an already-managed JPA `UserEntity` in place with values
     * from a domain `User` object.
     *
     * 🧠 Why this exists:
     *  - Prevents creating a *new entity instance* (which would cause `INSERT` instead of `UPDATE`).
     *  - Works safely within the current persistence context.
     *
     * ⚠️ Ignored fields:
     *  - `id` → preserves DB primary key.
     *  - `publicId` → keeps immutable UUID.
     *  - `authorities` → handled separately to attach managed AuthorityEntity instances.
     *  - `createdDate` / `lastModifiedDate` → managed by JPA auditing.
     */
    // NEW: update an existing UserEntity with values from domain
    @Mapping(target = "id", ignore = true)              // keep DB id untouched
    @Mapping(target = "publicId", ignore = true)        // don’t overwrite PKs/identifiers
    @Mapping(target = "authorities", ignore = true)     // handled separately
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    void updateUserEntityFromDomain(User updatedUserDetails, @MappingTarget UserEntity entity);

    // Map UserEntity -> User domain (reverse)
    @Mapping(target = "publicId", qualifiedByName = "uuidToString")
    @Mapping(target = "authorities", source = "authorities")
    User mapUserEntityToUserDomain(UserEntity userEntity);

    // Map AuthorityEntity -> Authority domain (reverse)
    Authority mapAuthorityEntityToAuthorityDomain(AuthorityEntity authorityEntity);

    // Map a set of AuthorityEntities -> Authorities (domain)
    default Set<Authority> mapAuthorityEntitiesToAuthorities(Set<AuthorityEntity> authorityEntities) {
        if (authorityEntities == null) return null;
        return authorityEntities.stream()
                .map(this::mapAuthorityEntityToAuthorityDomain)
                .collect(Collectors.toSet());
    }

    // Helper to convert String -> UUID
    @Named("stringToUUID")
    default UUID stringToUUID(String publicId) {
        return publicId == null ? null : UUID.fromString(publicId);
    }

    // Helper to convert UUID -> String
    @Named("uuidToString")
    default String uuidToString(UUID publicId) {
        return publicId == null ? null : publicId.toString();
    }
}
