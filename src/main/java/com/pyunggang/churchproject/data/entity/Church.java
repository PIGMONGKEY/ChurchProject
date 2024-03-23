package com.pyunggang.churchproject.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

@Entity
@Table(name = "TBL_CHURCH")
@Getter
@Setter
public class Church {
    @Id
    private String church;

    @NotNull
    private String password;
}
