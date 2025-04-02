package org.example.pastebinrestapi.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_pastes")
public class UserPasteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_pastes_seq")
    @SequenceGenerator(
            name = "user_pastes_seq",
            sequenceName = "user_pastes_seq",
            allocationSize = 50
    )
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paste_id")
    private PasteEntity paste;

    @Getter
    @Setter
    @Embeddable
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserPasteId {
        private Long userId;
        private Long pasteId;
    }
}
