package com.pyunggang.churchproject.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "TBL_CHURCH")
@Getter
@Setter
public class Church {
    @Id
    private String name;

    @Column(nullable = false)
    private String password;
}
