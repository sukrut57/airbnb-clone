package com.airbnb.clone.backend.user.application.mapper;

import com.airbnb.clone.backend.user.adapter.out.persistence.entities.AuthorityEntity;
import com.airbnb.clone.backend.user.adapter.out.persistence.entities.UserEntity;
import com.airbnb.clone.backend.user.domain.model.Authority;
import com.airbnb.clone.backend.user.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    // Map User domain -> UserEntity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "publicId", qualifiedByName = "stringToUUID")
    @Mapping(target = "authorities", source = "authorities")
    UserEntity mapUserDomainToUserEntity(User user);

    // Map Authority domain -> AuthorityEntity
    @Mapping(target = "id", ignore = true)
    AuthorityEntity mapAuthorityDomainToAuthorityEntity(Authority authority);

    // Map a set of Authorities (domain) to AuthorityEntities
    default Set<AuthorityEntity> mapAuthorities(Set<Authority> authorities) {
        if (authorities == null) return null;
        return authorities.stream()
                .map(this::mapAuthorityDomainToAuthorityEntity)
                .collect(Collectors.toSet());
    }

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
