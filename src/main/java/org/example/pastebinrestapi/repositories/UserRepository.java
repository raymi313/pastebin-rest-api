package org.example.pastebinrestapi.repositories;

import org.example.pastebinrestapi.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.stream.Stream;

public interface UserRepository extends JpaRepository<UserEntity, Long>{

    Stream<UserEntity> streamAllBy();

    Stream<UserEntity> streamAllByUsernameStartsWithIgnoreCase(String prefixUsername);
}
