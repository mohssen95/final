package org.neshan.apireportservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.neshan.apireportservice.entity.model.enums.InteractionType;

@Entity
@Table(name = "interaction_tb")
@RequiredArgsConstructor
@Setter
@Getter
public class Interaction {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Enumerated(EnumType.STRING)
    private InteractionType interactionType;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne()
    @JoinColumn(name = "report_id")
    private Report report;

}
