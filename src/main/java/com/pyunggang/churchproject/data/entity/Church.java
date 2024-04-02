package com.pyunggang.churchproject.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Builder
    public Church(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
