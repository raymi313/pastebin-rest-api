package org.example.pastebinrestapi.repositories;

import org.example.pastebinrestapi.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.stream.Stream;

public interface UserRepository extends JpaRepository<UserEntity, Long>{

    Stream<UserEntity> streamAllBy();

    Stream<UserEntity> streamAllByUsernameStartsWithIgnoreCase(String prefixUsername);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByEmail(String email);

}
