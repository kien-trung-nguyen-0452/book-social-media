package org.example.userprofileservice.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserProfileUpdateResponse {
    String id;
    String newName;
}
