package com.pyunggang.churchproject.data.repository;

import com.pyunggang.churchproject.data.entity.Applyment;
import com.pyunggang.churchproject.data.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplymentRepository extends JpaRepository<Applyment, Integer> {
    /**
     * 중복 신청 확인
     * 모든 테이블 다 조인해서 확인함
     */
    default boolean existsByEventNameAndParticipant(String eventName, Participant participant) {
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

    /**
     * 종목명과 교회명으로 참가자 리스트 받기
     * @param eventName 종목명
     * @param churchName 교회명
     * @return Participant - List
     */
    List<Applyment> findAllByEventNameAndParticipantChurchName(String eventName, String churchName);

    /**
     * 종목명과 참가자 정보로 신청 정보 조회
     * @param participant 참가자 정보
     * @param eventName 종목명
     * @return 신청 정보
     */
    Applyment findByParticipantAndEventName(Participant participant, String eventName);

    /**
     * 참가자 정보로 신청 정보 유무 확인
     * @param participant 참가자 정보
     * @return 결과 boolean
     */
    boolean existsByParticipant(Participant participant);

    List<Applyment> findAllByParticipantChurchName(String churchName);

    List<Applyment> findAllByEventName(String eventName);
}
