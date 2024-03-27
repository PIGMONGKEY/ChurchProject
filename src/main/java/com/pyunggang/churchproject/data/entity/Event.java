package com.pyunggang.churchproject.data.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "TBL_EVENT")
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Event {
    @Id
    @Column(name = "event_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int eventId;

    @Column(nullable = false)
    private String name;

    @Builder
    public Event(String name) {
        this.name = name;
    }
}
