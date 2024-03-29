package com.pyunggang.churchproject.data.repository;

import com.pyunggang.churchproject.data.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Integer> {

    default boolean existsByParticipant(Participant participant) {
        return existsByNameAndAgeAndGenderAndGradeAndChurchNameAndDepartmentName(
                participant.getName(),
                participant.getAge(),
                participant.getGender(),
                participant.getGrade(),
                participant.getChurch().getName(),
                participant.getDepartment().getName()
        );
    }

    default Participant findParticipantByParticipant(Participant participant) {
        return findParticipantByNameAndAgeAndGenderAndGradeAndChurchNameAndDepartmentName(
                participant.getName(),
                participant.getAge(),
                participant.getGender(),
                participant.getGrade(),
                participant.getChurch().getName(),
                participant.getDepartment().getName()
        );
    }

    boolean existsByNameAndAgeAndGenderAndGradeAndChurchNameAndDepartmentName(String name,
                                                                              int age,
                                                                              String gender,
                                                                              int grade,
                                                                              String churchName,
                                                                              String departmentName);

    Participant findParticipantByNameAndAgeAndGenderAndGradeAndChurchNameAndDepartmentName(String name,
                                                                                           int age,
                                                                                           String gender,
                                                                                           int grade,
                                                                                           String churchName,
                                                                                           String departmentName);
}
