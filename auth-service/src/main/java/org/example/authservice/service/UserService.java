package org.example.authservice.service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.example.authservice.dto.request.UserCreateRequest;
import org.example.authservice.dto.request.UserUpdateRequest;
import org.example.authservice.dto.response.UserResponse;
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
import org.springframework.security.access.prepost.PostAuthorize;
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

    public UserResponse createUser(UserCreateRequest request) {
        if(userRepository.existsByUsername(request.getUsername())){
            throw new ServiceException(ErrorCode.USER_EXISTED);
        }
        if(userRepository.existsByEmail(request.getEmail())){
            throw new ServiceException(ErrorCode.EMAIL_EXISTED);
        }

        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        HashSet<Role> roles = new HashSet<>();

        roleRepository.findById(PredefinedRole.USER_ROLE).ifPresent(roles::add);

        user.setRoles(roles);

        try {
            user = userRepository.save(user);
        } catch (DataIntegrityViolationException exception){
            throw new ServiceException(ErrorCode.USER_EXISTED);
        }

        var profileRequest = profileMapper.toProfileCreationRequest(request);
        profileRequest.setUserId(user.getUserId());
        var profile = userProfileClient.createUserProfile(profileRequest);

        var userCreationResponse = userMapper.toUserResponse(user);
        userCreationResponse.setProfileId(profile.getResult().getId());

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
    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        User user =
                userRepository.findByUsername(name).orElseThrow(() -> new ServiceException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toUserResponse(user);
    }

    public UserResponse updateUser(UserUpdateRequest userUpdateRequest, String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ServiceException(ErrorCode.USER_NOT_EXISTED));

        userMapper.updateUser(userUpdateRequest, user);
        return userMapper.toUserResponse(userRepository.save(user));
    }
}
