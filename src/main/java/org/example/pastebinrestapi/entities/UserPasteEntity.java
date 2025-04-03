package org.example.pastebinrestapi.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_pastes")
public class UserPasteEntity {

    @EmbeddedId
    private UserPasteId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("pasteId")
    @JoinColumn(name = "paste_id", referencedColumnName = "id")
    private PasteEntity paste;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Embeddable
    public static class UserPasteId implements Serializable {
        private Long userId;
        private Long pasteId;
    }
}
