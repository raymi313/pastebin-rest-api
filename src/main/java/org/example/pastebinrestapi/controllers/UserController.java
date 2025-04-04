package org.example.pastebinrestapi.controllers;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.pastebinrestapi.controllers.helpers.ControllerHelper;
import org.example.pastebinrestapi.dto.UserDto.UserResponseDto;
import org.example.pastebinrestapi.entities.UserEntity;
import org.example.pastebinrestapi.exceptions.BadRequestException;
import org.example.pastebinrestapi.exceptions.NotFoundException;
import org.example.pastebinrestapi.factories.UserDtoFactory;
import org.example.pastebinrestapi.repositories.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@Transactional
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    private final UserDtoFactory userDtoFactory;


    private static final String GET_USERS = "/api/users";

    @GetMapping(GET_USERS)
    public List<UserResponseDto> getUsers(@RequestParam(value = "username", required = false) Optional<String> prefixUsername){

        prefixUsername = prefixUsername.filter(name -> !name.trim().isEmpty());

        Stream<UserEntity> userStream = prefixUsername
                .map(userRepository::streamAllByUsernameStartsWithIgnoreCase)
                .orElseGet(userRepository::streamAllBy);


        return userStream
                .map(userDtoFactory::makeUserResponseDto)
                .collect(Collectors.toList());
    }
}
