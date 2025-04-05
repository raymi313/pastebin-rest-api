package org.example.pastebinrestapi.controllers;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.pastebinrestapi.controllers.helpers.ControllerHelper;
import org.example.pastebinrestapi.dto.UserDto.UserRequestDto;
import org.example.pastebinrestapi.dto.UserDto.UserResponseDto;
import org.example.pastebinrestapi.entities.UserEntity;
import org.example.pastebinrestapi.exceptions.BadRequestException;
import org.example.pastebinrestapi.factories.UserDtoFactory;
import org.example.pastebinrestapi.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    private final PasswordEncoder passwordEncoder;

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
    public UserRequestDto createOrUpdateUser(
            @RequestParam(value = "username", required = false) Optional<String> optionalUsername,
            @RequestParam(value = "password", required = false) Optional<String> optionalPassword,
            @RequestParam(value = "id", required = false) Optional<Long> userId,
            @RequestParam(value = "email", required = false) Optional<String> optionalEmail,
            @RequestParam(value = "isActive", required = false) Boolean isActive) {

        optionalUsername = optionalUsername.filter(name -> !name.trim().isEmpty());

        if (!userId.isPresent() && !optionalUsername.isPresent()) {
            throw new BadRequestException("Username can't be empty");
        }

        final UserEntity user = userId
                .map(helper::getUserOrThrowException)
                .orElseGet(() -> UserEntity.builder()
                        .createdAt(Instant.now())
                        .build());

        user.setActive(isActive != null ? isActive : user.isActive());

        optionalUsername.ifPresent(name -> {
            userRepository.findByUsername(name)
                    .filter(anotherUser -> !Objects.equals(anotherUser.getId(), user.getId()))
                    .ifPresent(anotherUser -> {
                        throw new BadRequestException(
                                String.format("Username '%s' already exists", name)
                        );
                    });
            user.setUsername(name);
        });

        optionalPassword.ifPresent(password -> {

            if (password.length() < 6) {
                throw new BadRequestException("Password must be at least 6 characters long");
            }

            user.setPassword(passwordEncoder.encode(password));
        });

        optionalEmail.ifPresent(email -> {
            userRepository.findByEmail(email)
                    .filter(anotherUser -> !Objects.equals(anotherUser.getId(), user.getId()))
                    .ifPresent(anotherUser -> {
                        throw new BadRequestException(
                                String.format("Email '%s' already exists", email)
                        );
                    });
            user.setEmail(email);
        });

        final UserEntity savedEntity = userRepository.save(user);
        return userDtoFactory.makeUserRequestDto(savedEntity);
    }
}
