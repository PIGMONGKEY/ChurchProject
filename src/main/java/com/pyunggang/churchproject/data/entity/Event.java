package com.pyunggang.churchproject.data.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Table(name = "TBL_EVENT")
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Event {
    @Id
    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "event")
    private List<Applyment> participants = new ArrayList<>();

    @Builder
    public Event(String name) {
        this.name = name;
    }
}
