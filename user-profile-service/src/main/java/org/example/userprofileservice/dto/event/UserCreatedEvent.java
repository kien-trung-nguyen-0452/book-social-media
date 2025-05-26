package org.example.userprofileservice.dto.event;
import java.time.LocalDate;

import lombok.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCreatedEvent implements Serializable {
    private String userId;
    private String username;
    private String name;
    private String email;
    private LocalDate createdAt;
}
