package org.example.pastebinrestapi.controllers;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.pastebinrestapi.controllers.helpers.ControllerHelper;
import org.example.pastebinrestapi.dto.UserDto.UserRequestDto;
import org.example.pastebinrestapi.dto.UserDto.UserResponseDto;
import org.example.pastebinrestapi.entities.PasteEntity;
import org.example.pastebinrestapi.entities.UserEntity;
import org.example.pastebinrestapi.exceptions.BadRequestException;
import org.example.pastebinrestapi.exceptions.NotFoundException;
import org.example.pastebinrestapi.factories.UserDtoFactory;
import org.example.pastebinrestapi.repositories.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@Transactional
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    private final UserDtoFactory userDtoFactory;

    private final ControllerHelper helper;

    private static final String GET_USERS = "/api/users";
    private static final String CREATE_OR_UPDATE_USER = "/api/users";

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

    @PutMapping(CREATE_OR_UPDATE_USER)
    public UserRequestDto createOrUpdateUser(@RequestParam(value = "username", required = false) Optional<String> optionalUsername,
                                             @RequestParam(value = "password", required = false) Optional <String> optionalPassword,
                                             @RequestParam(value = "id", required = false) Optional <Long> userId,
                                             @RequestParam(value = "email", required = false) Optional <String> optionalEmail,
                                             @RequestParam(value = "isActive", required = false) Boolean isActive,
                                             Instant userCreatedAt){

        optionalUsername = optionalUsername.filter(name -> !name.trim().isEmpty());

        if (!userId.isPresent() && !optionalUsername.isPresent()){
            throw new BadRequestException("Username can't be empty");
        }

        final UserEntity user = userId
                .map(helper::getUserOrThrowException)
                .orElseGet(() -> UserEntity.builder().build());

        userCreatedAt = Instant.now();
        user.setCreatedAt(userCreatedAt);
        user.setActive(isActive);

        optionalUsername.ifPresent( name ->{
                    userRepository.findByUsername(name)
                    .filter(anotherUser -> !Objects.equals(anotherUser.getId() , user.getId()))
                    .ifPresent(
                            anotherUser -> {
                                throw new BadRequestException(
                                        String.format("Username '%s' already exist", name)
                                );
                            }
                    );

            user.setUsername(name);

        });

        optionalPassword.ifPresent( password ->{
            userRepository.findByPassword(password)
                    .filter(anotherPassword -> !Objects.equals(anotherPassword.getId() , user.getId()))
                    .ifPresent(
                            anotherPassword -> {
                                throw new BadRequestException(
                                        String.format("Password '%s' already exist", password)
                                );
                            }
                    );

            user.setPassword(password);

        });

        optionalEmail.ifPresent( email ->{
            userRepository.findByEmail(email)
                    .filter(anotherEmail -> !Objects.equals(anotherEmail.getId() , user.getId()))
                    .ifPresent(
                            anotherEmail -> {
                                throw new BadRequestException(
                                        String.format("Email '%s' already exist", email)
                                );
                            }
                    );

            user.setEmail(email);

        });

        final UserEntity savedEntity = userRepository.save(user);

        return userDtoFactory.makeUserRequestDto(savedEntity);
    }
}
