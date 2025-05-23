package org.example.userprofileservice.repository;

import org.example.userprofileservice.entity.UserProfile;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Optional;


public interface UserProfileRepository extends Neo4jRepository<UserProfile, String> {
    boolean existsUserProfileByUserId(String userId);
    UserProfile findUserProfileByUserId(String userId);


}
