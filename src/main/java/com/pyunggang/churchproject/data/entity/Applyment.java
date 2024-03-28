package com.pyunggang.churchproject.data.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "TBL_APPLYMENT")
@Entity
public class Applyment {
    @Id
    @Column(name = "ep_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int epNo;

    @ManyToOne
    @JoinColumn(name = "participant_id", referencedColumnName = "participant_id", nullable = false)
    private Participant participant;

    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "event_id", nullable = false)
    private Event event;

    @Builder
    public Applyment(Participant participant, Event event) {
        this.participant = participant;
        this.event = event;
    }
}
