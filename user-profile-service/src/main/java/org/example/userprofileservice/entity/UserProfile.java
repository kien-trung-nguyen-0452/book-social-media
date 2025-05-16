package org.example.userprofileservice.entity;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node("User")
@Data
public class UserProfile {
    @Id
    String id;
    String userId;
    String name;
    String avatarUrl;
}
