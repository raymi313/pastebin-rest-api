package org.example.pastebinrestapi.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pastes")
public class PasteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pastes_seq")
    @SequenceGenerator(
            name = "pastes_seq",
            sequenceName = "pastes_seq",
            allocationSize = 50
    )
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Builder.Default
    private Instant createdAt = Instant.now();

    private Instant updatedAt;

    private boolean isPrivate = false;

}
