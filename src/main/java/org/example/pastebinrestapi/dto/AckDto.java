package org.example.pastebinrestapi.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AckDto {

    private boolean answer;

    public static AckDto makeDefaults(boolean answer){
        return builder()
                .answer(answer)
                .build();
    }
}
