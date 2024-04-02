package com.pyunggang.churchproject.data.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "TBL_DEPARTMENT")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Department {
    @Id
    @Column(nullable = false)
    private String name;
}
