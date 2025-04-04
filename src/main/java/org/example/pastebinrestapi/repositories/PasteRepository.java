package org.example.pastebinrestapi.repositories;

import org.example.pastebinrestapi.entities.PasteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.stream.Stream;

public interface PasteRepository extends JpaRepository<PasteEntity, Long> {

    Stream<PasteEntity> streamAllByTitleStartsWithIgnoreCase(String prefixTitle);

    Stream<PasteEntity> streamAllBy();

    Optional<PasteEntity> findByTitle(String pasteTitle);

    Optional<PasteEntity> findByContent(String pasteContent);

}
