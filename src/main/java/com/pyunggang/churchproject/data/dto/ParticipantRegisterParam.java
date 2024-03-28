package com.pyunggang.churchproject.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParticipantRegisterParam {
    private String churchName;
    private String eventName;
    private String department;
    private String name;
    private int age;
    private int grade;
    private String gender;
}
