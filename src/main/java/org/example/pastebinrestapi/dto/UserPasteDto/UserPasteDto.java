package org.example.pastebinrestapi.dto.UserPasteDto;


import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPasteDto {

    @NonNull
    private String userId;

    @NonNull
    private String pasteId;
}
