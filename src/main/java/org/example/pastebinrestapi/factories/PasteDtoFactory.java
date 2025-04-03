package org.example.pastebinrestapi.factories;

import org.example.pastebinrestapi.dto.PasteDto.PasteRequestDto;
import org.example.pastebinrestapi.dto.PasteDto.PasteResponseDto;
import org.example.pastebinrestapi.dto.PasteDto.PasteShortResponseDto;
import org.example.pastebinrestapi.entities.PasteEntity;
import org.springframework.stereotype.Component;

@Component
public class PasteDtoFactory {

    public PasteRequestDto makePastRequestDto(PasteEntity entity){
        return PasteRequestDto.builder()
                .id(entity.getId())
                .content(entity.getContent())
                .title(entity.getTitle())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .isPrivate(entity.isPrivate())
                .build();
    }

    public PasteResponseDto makePastResponseDto(PasteEntity entity){
        return PasteResponseDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .isPrivate(entity.isPrivate())
                .build();
    }

    public PasteShortResponseDto makePastShortResponseDto(PasteEntity entity){
        return PasteShortResponseDto.builder()
                .id(entity.getId())
                .content(entity.getContent())
                .title(entity.getTitle())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .isPrivate(entity.isPrivate())
                .build();
    }


}
