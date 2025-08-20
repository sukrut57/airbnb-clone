package com.airbnb.clone.backend.user.adapter.out.persistence.entities;

import com.airbnb.clone.backend.shared.domain.AbstractAuditingEntity;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
public class UserEntity extends AbstractAuditingEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "profile_picture_url")
    private String profilePictureUrl;

    @Column(name= "account_enabled", nullable = false)
    private boolean accountEnabled;

    @Column(name = "public_id", nullable = false, unique = true)
    private UUID publicId;


    /*
    * PERSIST ensures new authorities are saved automatically when saving a new user.
    * MERGE ensures changes to authorities are updated when updating a user.
    */
    /* many-to-many relationship */
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "user_authority",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "name")})
    private Set<AuthorityEntity> authorities = new HashSet<>();

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public boolean isAccountEnabled() {
        return accountEnabled;
    }

    public void setAccountEnabled(boolean accountEnabled) {
        this.accountEnabled = accountEnabled;
    }

    public UUID getPublicId() {
        return publicId;
    }

    public void setPublicId(UUID publicId) {
        this.publicId = publicId;
    }

    public Set<AuthorityEntity> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<AuthorityEntity> authorities) {
        this.authorities = authorities;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity userEntity = (UserEntity) o;
        return accountEnabled == userEntity.accountEnabled && Objects.equals(email, userEntity.email) && Objects.equals(firstName, userEntity.firstName) && Objects.equals(lastName, userEntity.lastName) && Objects.equals(profilePictureUrl, userEntity.profilePictureUrl) && Objects.equals(publicId, userEntity.publicId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, firstName, lastName, profilePictureUrl, accountEnabled, publicId);
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", profilePictureUrl='" + profilePictureUrl + '\'' +
                ", accountEnabled=" + accountEnabled +
                ", publicId=" + publicId +
                '}';
    }
}
