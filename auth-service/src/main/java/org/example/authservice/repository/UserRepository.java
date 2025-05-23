package org.example.authservice.repository;

import java.util.Optional;

import org.example.authservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<User> findByUsername(String username);


    @Query("SELECT u.userId FROM User u WHERE u.username = :username")
    Optional<String> getUserIdByUsername(@Param("username") String username);

}
