package com.pyunggang.churchproject.data.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "TBL_DEPARTMENT")
@NoArgsConstructor
public class Department {
    @Id
    @Column(nullable = false)
    private String name;

    @Column(name = "min_age", nullable = false)
    private int minAge;

    @Column(name = "max_age", nullable = false)
    private int maxAge;

    @Builder
    public Department(String name, int minAge, int maxAge) {
        this.name = name;
        this.minAge = minAge;
        this.maxAge = maxAge;
    }
}
