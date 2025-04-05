package org.example.pastebinrestapi.dto.UserDto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {

    @NonNull
    private Long id;

    @NonNull
    private String username;

    @NonNull
    private String email;

    @NonNull
    private String password;

    @NonNull
    @JsonProperty("created_at")
    private Instant createdAt = Instant.now();

    @NonNull
    private boolean isActive = true;
}
