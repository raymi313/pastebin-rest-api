package org.example.pastebinrestapi.factories;

import org.example.pastebinrestapi.dto.UserDto.UserLoginRequestDto;
import org.example.pastebinrestapi.dto.UserDto.UserRequestDto;
import org.example.pastebinrestapi.dto.UserDto.UserResponseDto;
import org.example.pastebinrestapi.entities.UserEntity;

public class UserDtoFactory {

    UserRequestDto makeUserRequestDto(UserEntity entity){
        return UserRequestDto.builder()
                .username(entity.getUsername())
                .password(entity.getPassword())
                .email(entity.getEmail())
                .build();
    }

    UserResponseDto makeUserResponseDto(UserEntity entity){
        return UserResponseDto.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .password(entity.getPassword())
                .email(entity.getEmail())
                .createdAt(entity.getCreatedAt())
                .isActive(entity.isActive())
                .build();
    }

    UserLoginRequestDto makeUserLoginRequestDto(UserEntity entity){
        return  UserLoginRequestDto.builder()
                .username(entity.getUsername())
                .password(entity.getPassword())
                .build();
    }
}
