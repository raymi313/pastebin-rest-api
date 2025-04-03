package org.example.pastebinrestapi.controllers;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.pastebinrestapi.controllers.helpers.ControllerHelper;
import org.example.pastebinrestapi.dto.AckDto;
import org.example.pastebinrestapi.dto.PasteDto.PasteShortResponseDto;
import org.example.pastebinrestapi.entities.PasteEntity;
import org.example.pastebinrestapi.factories.PasteDtoFactory;
import org.example.pastebinrestapi.repositories.PasteRepository;
import org.example.pastebinrestapi.repositories.UserPasteRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@Transactional
@RequiredArgsConstructor
public class PasteController {

    private final PasteRepository pasteRepository;

    private final UserPasteRepository userPasteRepository;

    private final PasteDtoFactory pasteDtoFactory;

    private final ControllerHelper helper;

    public static final String FETCH_PASTE = "/api/pastes";
    public static final String DELETE_PASTE = "/api/pastes/{paste_id}";

    @GetMapping(FETCH_PASTE)
    public List<PasteShortResponseDto> fetchPastes(@RequestParam(value = "title",required = false) Optional<String> prefixTitle){

        prefixTitle = prefixTitle.filter(title -> !title.trim().isEmpty());

        Stream<PasteEntity> pasteStream = prefixTitle
                .map(pasteRepository::streamAllByTitleStartsWithIgnoreCase)
                .orElseGet(pasteRepository::streamAllBy);

        return pasteStream
                .map(pasteDtoFactory::makePastShortResponseDto)
                .collect(Collectors.toList());
    }

    @DeleteMapping(DELETE_PASTE)
    public AckDto deletePaste(@PathVariable("paste_id") Long pasteId){

        helper.getPasteOrThrowException(pasteId);

        userPasteRepository.deleteByPasteId(pasteId);

        pasteRepository.deleteById(pasteId);

        return AckDto.makeDefaults(true);
    }

}
