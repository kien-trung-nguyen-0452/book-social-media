package org.example.authservice.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChangePasswordRequest {
    String currentPassword;
    String newPassword;
}
