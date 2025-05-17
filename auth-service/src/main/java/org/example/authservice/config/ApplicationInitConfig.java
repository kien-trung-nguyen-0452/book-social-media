package org.example.authservice.config;

import java.util.HashSet;

import org.example.authservice.dto.request.UserProfileCreationRequest;
import org.example.authservice.entity.Role;
import org.example.authservice.entity.User;
import org.example.authservice.enumerate.PredefinedRole;
import org.example.authservice.repository.RoleRepository;
import org.example.authservice.repository.UserRepository;
import org.example.authservice.repository.httpClient.UserProfileClient;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {

    PasswordEncoder passwordEncoder;

    @NonFinal
    static final String ADMIN_USER_NAME = "admin";

    @NonFinal
    static final String ADMIN_PASSWORD = "admin";

    @Bean
    @ConditionalOnProperty(
            prefix = "spring",
            value = "datasource.driverClassName",
            havingValue = "com.mysql.cj.jdbc.Driver")

    ApplicationRunner applicationRunner(UserProfileClient userProfileClient, UserRepository userRepository, RoleRepository roleRepository) {
        log.info("Initializing application.....");
        return args -> {
            if (userRepository.findByUsername(ADMIN_USER_NAME).isEmpty()) {
                Role userRole = roleRepository.save(Role.builder()
                        .roleName(PredefinedRole.USER_ROLE)
                        .description("User role")
                        .build());

                Role adminRole = roleRepository.save(Role.builder()
                        .roleName(PredefinedRole.ADMIN_ROLE)
                        .description("Admin role")
                        .build());

                var roles = new HashSet<Role>();
                roles.add(adminRole);
                roles.add(userRole);

                User user = User.builder()
                        .username(ADMIN_USER_NAME)
                        .password(passwordEncoder.encode(ADMIN_PASSWORD))
                        .roles(roles)
                        .build();

                userRepository.save(user);
                userProfileClient.createUserProfile(UserProfileCreationRequest.builder()
                                .email(user.getEmail())
                                .name(user.getUsername())
                                .userId(user.getUserId())
                                .build());
                log.warn("admin user has been created with default password: admin, please change it");
            }
            log.info("Application initialization completed .....");
        };
    }
}
