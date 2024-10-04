package com.pyunggang.churchproject.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TBL_CATEGORY")
@Getter
@Setter
@NoArgsConstructor
public class Category {
    @Id
    @Column(name = "category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "event_name", referencedColumnName = "name", nullable = false)
    private Event event;

    @OneToMany(mappedBy = "category")
    private List<Applyment> applyments = new ArrayList<>();
}
