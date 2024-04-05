package com.pyunggang.churchproject.service.impl;

import com.pyunggang.churchproject.data.dto.ApplymentParam;
import com.pyunggang.churchproject.data.entity.Applyment;
import com.pyunggang.churchproject.data.entity.Participant;
import com.pyunggang.churchproject.data.repository.*;
import com.pyunggang.churchproject.service.ApplymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApplymentServiceImpl implements ApplymentService {
    final private ApplymentRepository applyRepo;
    final private ParticipantRepository partiRepo;
    final private ChurchRepository churchRepo;
    final private DepartmentRepository departRepo;
    final private EventRepository eventRepo;

    /**
     * 참가자 정보 리스트로 받아서 insert
     * @param params List<ParticipantRegisterParam> 형태로 참가자 정보를 받음
     * @return 성공하면 true, 실패하면 false
     */
//    TODO: 동명이인 어떡할지 고민 필요
//    TODO: 런타임 에러 발생시켜서 롤백 시킬 방법 찾기
    @Override
    public ResponseEntity saveApplyment(List<ApplymentParam> params) {
        Participant participant;
        Applyment applyment;
        HttpStatus status = HttpStatus.OK;

        for (ApplymentParam param : params) {
            // 빈 데이터가 있는 참가자 정보 건너뛰기
            if (param.getName().equals("") || param.getGender().equals("") || param.getDepartment().equals("")
                    || param.getGrade() == 0 || param.getAge() == 0) {
                continue;
            }
            // 새로운 참가자 객체 생성
            participant = Participant.builder()
                    .department(departRepo.findDepartmentByNameIs(param.getDepartment()))
                    .church(churchRepo.findChurchByNameIs(param.getChurchName()))
                    .name(param.getName())
                    .age(param.getAge())
                    .gender(param.getGender())
                    .grade(param.getGrade())
                    .build();

            // 이미 등록된 참가자가 아니라면,
            if (!partiRepo.existsByParticipant(participant)) {
                // Participant에 삽입 / 실패하면 false 리턴
                if (!partiRepo.save(participant).getName().equals(param.getName())) {
                    status = HttpStatus.INTERNAL_SERVER_ERROR;
                    break;
                }
            } else {
                // 이미 등록된 참가자라면,
                // 중복 신청인지, 다른 종목에 참여하는 같은 참가자인지 확인
                // 중복 신청이라면 건너뜀
                if (applyRepo.existsByEventNameAndParticipant(param.getEventName(), participant))
                    continue;

                // 저장되어 있는 Participant를 불러온다
                participant = partiRepo.findParticipantByParticipant(participant);
            }

            // 신청 생성
            applyment = Applyment.builder()
                    .event(eventRepo.findEventByNameIs(param.getEventName()))
                    .participant(participant)
                    .createTime(LocalDateTime.now())
                    .updateTime(LocalDateTime.now())
                    .build();

            // Applyment에 삽입 / 실패하면 return false
            if (!applyRepo.save(applyment).getParticipant().getName().equals(param.getName())) {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
                break;
            }
        }

        return new ResponseEntity(status);
    }

    /**
     * 교회명과 종목명으로 신청목록 전부 불러오기
     * @param churchName 교회명
     * @param eventName 종목명
     * @return ParticipantRegisterParam - List 형태로 리턴
     */
    @Override
    public ResponseEntity<List<ApplymentParam>> findApplymentList(String churchName, String eventName) {
        HttpStatus status = HttpStatus.OK;
        // 빈 linked list 생성
        List<ApplymentParam> params = new LinkedList<>();
        List<Applyment> applyments = applyRepo.findAllByEventNameAndParticipantChurchName(eventName, churchName).orElseGet(null);

        if (applyments == null)
            status = HttpStatus.NOT_FOUND;
        else {
            for (Applyment applyment : applyments) {
                params.add(new ApplymentParam(applyment));
            }
        }

        return new ResponseEntity<>(params, status);
    }

    /**
     * 종목명과 참가자 id로 신청 내역 삭제
     * 2종목 이상 참여할 경우, 신청 내역만 삭제
     * 1종목만 참여할 경우, 참가자 정보까지 삭제
     * @param eventName 종목명
     * @param participantId 참가자id
     * @return
     */
    @Override
    @Transactional
    public ResponseEntity deleteApplyment(String eventName, int participantId) {
        HttpStatus status = HttpStatus.OK;
        // id로 참가자 정보 불러오기
        Participant participant = partiRepo.findById(participantId).orElseGet(null);
        // applyment 정보 불러오기
        Applyment applyment = applyRepo.findByParticipantAndEventName(participant, eventName).orElse(null);

        if (participant == null || applyment == null) {
            status = HttpStatus.BAD_REQUEST;
        } else {
            // 신청정보 삭제
            applyRepo.delete(applyment);

            // 같은 참가자가 다른 종목을 신청하지 않았으면, 참가자 정보 삭제
            if (!applyRepo.existsByParticipant(participant)) {
                partiRepo.delete(participant);
            }
        }

        return new ResponseEntity(status);
    }

    @Override
    @Transactional
    public ResponseEntity updateApplyment(ApplymentParam applymentParam) {
        HttpStatus status = HttpStatus.CREATED;
        Participant participant = partiRepo.findById(applymentParam.getId()).orElseGet(null);

        if (participant == null)
            status = HttpStatus.BAD_REQUEST;
        else {
            participant.setAge(applymentParam.getAge());
            participant.setName(applymentParam.getName());
            participant.setGender(applymentParam.getGender());
            participant.setGrade(applymentParam.getGrade());
            participant.setDepartment(departRepo.findDepartmentByNameIs(applymentParam.getDepartment()));

            if (partiRepo.save(participant).getParticipantId() != participant.getParticipantId())
                status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity(status);
    }
}
