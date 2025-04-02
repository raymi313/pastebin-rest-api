package org.example.pastebinrestapi.dto.PasteDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.Instant;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PasteShortResponseDto {

    @NonNull
    private Long id;

    @NonNull
    private String title;

    @NonNull
    private String content;

    @NonNull
    @JsonProperty("created_at")
    private Instant createdAt = Instant.now();

    @NonNull
    @JsonProperty("updated_at")
    private Instant updatedAt;

    private boolean isPrivate = false;
}
