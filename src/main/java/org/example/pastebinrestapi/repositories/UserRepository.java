package org.example.pastebinrestapi.repositories;

import org.example.pastebinrestapi.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long>{
}
