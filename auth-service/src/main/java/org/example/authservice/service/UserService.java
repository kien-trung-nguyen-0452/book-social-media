package org.example.authservice.service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.example.authservice.dto.request.ChangePasswordRequest;
import jakarta.transaction.Transactional;
import org.example.authservice.dto.common.ApiResponse;
import org.example.authservice.dto.request.UserCreateRequest;
import org.example.authservice.dto.request.UserDeleteRequest;
import org.example.authservice.dto.request.UserUpdateRequest;
import org.example.authservice.dto.response.*;
import org.example.authservice.entity.Role;
import org.example.authservice.entity.User;
import org.example.authservice.enumerate.PredefinedRole;
import org.example.authservice.exception.ErrorCode;
import org.example.authservice.exception.ServiceException;
import org.example.authservice.mapper.ProfileMapper;
import org.example.authservice.mapper.UserMapper;
import org.example.authservice.repository.RoleRepository;
import org.example.authservice.repository.UserRepository;
import org.example.authservice.repository.httpClient.UserProfileClient;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    UserProfileClient userProfileClient;
    ProfileMapper profileMapper;
    RoleRepository roleRepository;
    UserKafkaProducer userKafkaProducer;
    @Transactional
    public UserResponse createUser(UserCreateRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new ServiceException(ErrorCode.USER_EXISTED);
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ServiceException(ErrorCode.EMAIL_EXISTED);
        }

        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        HashSet<Role> roles = new HashSet<>();

        roleRepository.findById(PredefinedRole.USER_ROLE).ifPresent(roles::add);

        user.setRoles(roles);

        try {
            user = userRepository.save(user);
        } catch (DataIntegrityViolationException exception) {
            throw new ServiceException(ErrorCode.USER_EXISTED);
        }

        var profileRequest = profileMapper.toProfileCreationRequest(request);
        profileRequest.setUserId(user.getUserId());
        log.info("creating user profile for user: {}", user.getUsername());
        UserProfileCreationResponse profile;
        try {
             profile = userProfileClient.createUserProfile(profileRequest).getResult();
        }catch (Exception ex) {
        // Gọi tạo profile thất bại -> Ném exception để rollback transaction (tức là rollback user tạo)
        log.error("Failed to create user profile for user: {}, rolling back user creation", user.getUsername(), ex);
        throw new ServiceException(ErrorCode.PROFILE_CREATE_FAIL);
    }


        var userCreationResponse = userMapper.toUserResponse(user);
        userCreationResponse.setProfileId(profile.getId());

        return userCreationResponse;
    }

    public List<UserResponse> findAllUser() {
        return userRepository.findAll().stream().map(userMapper::toUserResponse).collect(Collectors.toList());
    }

    @PostAuthorize("returnObject.username = authentication.name")
    public UserResponse findUserById(String id) {
        return userMapper.toUserResponse(
                userRepository.findById(id).orElseThrow(() -> new ServiceException(ErrorCode.USER_NOT_EXISTED)));
    }

    @PostAuthorize("returnObject != null and returnObject.username.equals(authentication.name)")
    public UserProfile getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        User user =
                userRepository.findByUsername(name).orElseThrow(() -> new ServiceException(ErrorCode.USER_NOT_EXISTED));
        UserProfileResponse userProfileResponse = userProfileClient.getMyInfo(user.getUserId()).getResult();
        return UserProfile.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .avatarUrl(userProfileResponse.getAvatarUrl())
                .createdAt(user.getCreatedAt())
                .name(userProfileResponse.getName())
                .build();
    }

    public UserResponse updateUser(UserUpdateRequest userUpdateRequest, String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ServiceException(ErrorCode.USER_NOT_EXISTED));

        userMapper.updateUser(userUpdateRequest, user);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public ChangePasswordResponse changePassword(String userId, ChangePasswordRequest request){
        User user = userRepository.findById(userId).orElseThrow(() -> new ServiceException(ErrorCode.USER_NOT_EXISTED));
        boolean isMatch = passwordEncoder.matches(request.getCurrentPassword(), user.getPassword());
        if(isMatch){
            return ChangePasswordResponse.builder()
                    .success(true)
                    .build();
        }
        else {
            throw new ServiceException(ErrorCode.INVALID_CURRENT_PASSWORD);
        }
    }


    @PostAuthorize("returnObject != null and returnObject.equals(authentication.name)")
    public String getUserId() {
        var authenticated = SecurityContextHolder.getContext().getAuthentication();
        if (authenticated == null || !authenticated.isAuthenticated()) {
            throw new ServiceException(ErrorCode.UNAUTHENTICATED);
        }

        String username = authenticated.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ServiceException(ErrorCode.USER_NOT_EXISTED));
        return user.getUserId();
    }
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUserProfile(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(ErrorCode.USER_NOT_EXISTED));
        try {
            log.info("Attempting to delete profile for user: {}", user.getUsername());

            ApiResponse<UserProfileDeleteResponse> response = userProfileClient.deleteUserProfile(userId);
            if (response == null || response.getCode() != 1000) {
                throw new ServiceException(ErrorCode.USER_PROFILE_DELETE_FAILED);
            }

            log.info("Deleted profile for user: {}", user.getUsername());

            userRepository.delete(user);
            log.info("Deleted user with ID: {}", userId);

            // ✅ Gửi sự kiện Kafka sau khi xóa thành công
            userKafkaProducer.sendUserDeletedEvent(userId);
            log.info("Sent user-deleted Kafka event for userId: {}", userId);

        } catch (Exception e) {
            log.error("Failed to delete user/profile for userId {}: {}", userId, e.getMessage());
            throw new ServiceException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }



}
