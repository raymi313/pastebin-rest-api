package org.example.pastebinrestapi.controllers.helpers;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.pastebinrestapi.entities.PasteEntity;
import org.example.pastebinrestapi.entities.UserEntity;
import org.example.pastebinrestapi.exceptions.NotFoundException;
import org.example.pastebinrestapi.repositories.PasteRepository;
import org.example.pastebinrestapi.repositories.UserRepository;
import org.springframework.stereotype.Component;



@Transactional
@Component
@RequiredArgsConstructor
public class ControllerHelper {

    private final PasteRepository pasteRepository;

    private final UserRepository userRepository;

    public PasteEntity getPasteOrThrowException(Long pasteId){
        return pasteRepository
                .findById(pasteId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Paste with this id is not found", pasteId)
                ));
    }

    public UserEntity getUserOrThrowException(Long userId){
        return userRepository
                .findById(userId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("User with this id is not found", userId)
                ));
    }
}
