package com.airbnb.clone.backend.user.adapter.out.persistence.entities;

import com.airbnb.clone.backend.shared.domain.AbstractAuditingEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.Objects;

@Entity
@Table(name = "authority")
public class AuthorityEntity {


    @Id
    @Size(max = 50)
    @Column(name = "name", nullable = false, unique = true, length = 50)
    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AuthorityEntity authorityEntity = (AuthorityEntity) o;
        return Objects.equals(name, authorityEntity.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }


    @Override
    public String toString() {
        return "Authority{" +
                ", name='" + name + '\'' +
                '}';
    }
}
