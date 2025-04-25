package org.example.authservice.entity;

import java.util.Set;

import jakarta.persistence.*;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Role {
    @Id
    String roleName;

    String description;

    @ManyToMany
    Set<Permission> permissions;
}
