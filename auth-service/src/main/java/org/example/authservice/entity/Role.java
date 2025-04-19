package org.example.authservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

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
