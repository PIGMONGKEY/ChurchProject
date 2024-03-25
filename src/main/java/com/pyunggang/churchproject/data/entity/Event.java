package com.pyunggang.churchproject.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TBL_EVENT")
@Getter
@Setter
@NoArgsConstructor
public class Event {
    @Id
    private String name;

    @Builder
    public Event(String name) {
        this.name = name;
    }
}
