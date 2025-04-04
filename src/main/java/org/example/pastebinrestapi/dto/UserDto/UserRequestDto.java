package org.example.pastebinrestapi.dto.UserDto;


import lombok.*;
import org.springframework.stereotype.Component;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {

    @NonNull
    private String username;

    @NonNull
    private String email;

    @NonNull
    private String password;
}
