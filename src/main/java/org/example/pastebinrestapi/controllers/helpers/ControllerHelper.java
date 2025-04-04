package org.example.pastebinrestapi.controllers.helpers;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.pastebinrestapi.entities.PasteEntity;
import org.example.pastebinrestapi.exceptions.NotFoundException;
import org.example.pastebinrestapi.repositories.PasteRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Transactional
@Component
@RequiredArgsConstructor
public class ControllerHelper {

    private final PasteRepository pasteRepository;

    public PasteEntity getPasteOrThrowException(Long pasteId){
        return pasteRepository
                .findById(pasteId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Paste with this id is not found", pasteId)
                ));
    }
}
