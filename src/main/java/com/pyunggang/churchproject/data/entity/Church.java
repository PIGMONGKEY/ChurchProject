package com.pyunggang.churchproject.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TBL_CHURCH")
@Getter
@Setter
public class Church {
    @Id
    private String church;

    @Column(nullable = false)
    private String password;
}
