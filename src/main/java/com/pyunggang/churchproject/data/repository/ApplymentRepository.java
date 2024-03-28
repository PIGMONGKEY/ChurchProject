package com.pyunggang.churchproject.data.repository;

import com.pyunggang.churchproject.data.entity.Applyment;
import com.pyunggang.churchproject.data.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplymentRepository extends JpaRepository<Applyment, Integer> {
    Applyment findApplymentByParticipantName(String name);

    /**
     * 중복 신청 확인
     * 모든 테이블 다 조인해서 확인함
     */
    default boolean existsByParticipant(String eventName, Participant participant) {
        return existsByEventNameAndParticipantNameAndParticipantAgeAndParticipantGradeAndParticipantGenderAndParticipantChurchName(
                eventName,
                participant.getName(),
                participant.getAge(),
                participant.getGrade(),
                participant.getGender(),
                participant.getChurch().getName()
        );
    }

    boolean existsByEventNameAndParticipantNameAndParticipantAgeAndParticipantGradeAndParticipantGenderAndParticipantChurchName(String eventName,
                                                                                                                                String participantName,
                                                                                                                                int participantAge,
                                                                                                                                int participantGrade,
                                                                                                                                String participantGender,
                                                                                                                                String participantChurchName);
}
