package org.example.userprofileservice.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserProfileUpdateRequest {
    String id;
    String newName;
}
