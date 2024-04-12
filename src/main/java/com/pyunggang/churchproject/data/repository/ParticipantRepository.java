package com.pyunggang.churchproject.data.repository;

import com.pyunggang.churchproject.data.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Integer> {

    // ID빼고 나머지 정보가 모두 일치하는 참가자가 있는지 확인
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

    // ID 빼고 나머지 정보가 모두 일치하는 참가자 정보 찾기
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

    // 교회명으로 참가자 검색
    List<Participant> findAllByChurchName(String churchName);

    // 부서명으로 참가자 검색
    List<Participant> findAllByDepartmentName(String departmentName);
}
