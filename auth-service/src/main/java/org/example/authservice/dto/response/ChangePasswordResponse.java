package org.example.authservice.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChangePasswordResponse {
    boolean success;
}
