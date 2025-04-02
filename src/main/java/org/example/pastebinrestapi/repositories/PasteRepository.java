package org.example.pastebinrestapi.repositories;

import org.example.pastebinrestapi.entities.PasteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasteRepository extends JpaRepository<PasteEntity, Long> {
}
