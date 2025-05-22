package org.example.userprofileservice.dto.request;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;


@Data
@Builder
public class UserProfileCreationRequest {
    String id;
    String userId;
    String name;
    String avatarUrl;
    @CreatedDate
    LocalDateTime createdAt;
}
