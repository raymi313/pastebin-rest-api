package org.example.pastebinrestapi.factories;

import org.example.pastebinrestapi.dto.UserDto.UserLoginRequestDto;
import org.example.pastebinrestapi.dto.UserDto.UserRequestDto;
import org.example.pastebinrestapi.dto.UserDto.UserResponseDto;
import org.example.pastebinrestapi.entities.UserEntity;

public class UserDtoFactory {

    public UserRequestDto makeUserRequestDto(UserEntity entity){
        return UserRequestDto.builder()
                .username(entity.getUsername())
                .password(entity.getPassword())
                .email(entity.getEmail())
                .build();
    }

    public UserResponseDto makeUserResponseDto(UserEntity entity){
        return UserResponseDto.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .password(entity.getPassword())
                .email(entity.getEmail())
                .createdAt(entity.getCreatedAt())
                .isActive(entity.isActive())
                .build();
    }

    public UserLoginRequestDto makeUserLoginRequestDto(UserEntity entity){
        return  UserLoginRequestDto.builder()
                .username(entity.getUsername())
                .password(entity.getPassword())
                .build();
    }
}
