package com.pyunggang.churchproject.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TBL_CHURCH")
@Getter
@Setter
@NoArgsConstructor
public class Church {
    @Id
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @JsonIgnore
    private String role;

    @Builder
    public Church(String name, String role, String password) {
        this.name = name;
        this.role = role;
        this.password = password;
    }
}
