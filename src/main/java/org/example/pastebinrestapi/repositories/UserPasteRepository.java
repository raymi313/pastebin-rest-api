package org.example.pastebinrestapi.repositories;

import org.example.pastebinrestapi.entities.UserPasteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPasteRepository extends JpaRepository<UserPasteEntity, Long> {
    void deleteByPasteId(Long pasteId);

    void deleteAllByPasteId(Long pasteId);
}
