package org.example.pastebinrestapi.dto.UserDto;


import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginRequestDto {

    @NonNull
    private String username;

    @NonNull
    private String password;
}
