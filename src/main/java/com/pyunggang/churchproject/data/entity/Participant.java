package com.pyunggang.churchproject.data.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Table(name = "TBL_PARTICIPANT")
@NoArgsConstructor
@Entity
public class Participant {
    @Id
    @Column(name = "participant_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int participantId;

    @ManyToOne
    @JoinColumn(name = "church_id", referencedColumnName = "church_id", nullable = false)
    private Church church;

    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "event_id", nullable = false)
    private Event event;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false)
    private int grade;

    @ManyToOne
    @JoinColumn(name = "department_id", referencedColumnName = "department_id", nullable = false)
    private Department department;

    @Column(nullable = false)
    private String gender;

    @Builder
    public Participant(Church church, Event event, String name, int age, int grade, Department department, String gender) {
        this.church = church;
        this.event = event;
        this.name = name;
        this.age = age;
        this.grade = grade;
        this.department = department;
        this.gender = gender;
    }
}
