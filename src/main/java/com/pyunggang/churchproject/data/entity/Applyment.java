package com.pyunggang.churchproject.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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
    @JoinColumn(name = "event_name", referencedColumnName = "name", nullable = false)
    private Event event;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "category_id", nullable = false)
    private Category category;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime createTime;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime updateTime;

    @Builder
    public Applyment(Participant participant, Event event, Category category, LocalDateTime createTime, LocalDateTime updateTime) {
        this.participant = participant;
        this.event = event;
        this.category = category;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }
}
