package com.pyunggang.churchproject.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "TBL_PARTICIPANT")
public class Participant {
    @Id
    private int participantNo;

    @Column
    private String church;

    @Column
    private String event;

    @Column
    private String name;

    @Column
    private int age;
}
