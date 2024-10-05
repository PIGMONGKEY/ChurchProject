package com.pyunggang.churchproject.data.dto;

import com.pyunggang.churchproject.data.entity.Applyment;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApplymentParam {
    private int id;
    private String churchName;
    private String eventName;
    private String department;
    private String categoryName;
    private String name;
    private int age;
    private int grade;
    private String gender;

    @Builder
    public ApplymentParam(int id, String churchName, String eventName, String department, String categoryName, String name, int age, int grade, String gender) {
        this.id = id;
        this.churchName = churchName;
        this.eventName = eventName;
        this.department = department;
        this.categoryName = categoryName;
        this.name = name;
        this.age = age;
        this.grade = grade;
        this.gender = gender;
    }

    public ApplymentParam(Applyment applyment) {
        this.id = applyment.getParticipant().getParticipantId();
        this.churchName = applyment.getParticipant().getChurch().getName();
        this.eventName = applyment.getEvent().getName();
        this.department = applyment.getParticipant().getDepartment().getName();
        this.categoryName = applyment.getCategory().getName();
        this.name = applyment.getParticipant().getName();
        this.age = applyment.getParticipant().getAge();
        this.grade = applyment.getParticipant().getGrade();
        this.gender = applyment.getParticipant().getGender();
    }
}
