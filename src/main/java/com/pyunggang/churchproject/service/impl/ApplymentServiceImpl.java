package com.pyunggang.churchproject.service.impl;

import com.pyunggang.churchproject.data.dto.ParticipantRegisterParam;
import com.pyunggang.churchproject.data.entity.Applyment;
import com.pyunggang.churchproject.data.entity.Participant;
import com.pyunggang.churchproject.data.repository.*;
import com.pyunggang.churchproject.service.ApplymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
    public boolean saveApplyment(List<ParticipantRegisterParam> params) {
        Participant participant;
        Applyment applyment;

        for (ParticipantRegisterParam param : params) {
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
                if (!partiRepo.save(participant).getName().equals(param.getName()))
                    return false;
            } else {
                // 이미 등록된 참가자라면,
                // 중복 신청인지, 다른 종목에 참여하는 같은 참가자인지 확인
                // 중복 신청이라면, return false
                if (applyRepo.existsByParticipant(param.getEventName(), participant)) {
                    return false;
                }

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
            if (!applyRepo.save(applyment).getParticipant().getName().equals(param.getName()))
                return false;
        }

        return true;
    }

    @Override
    public List<ParticipantRegisterParam> findApplymentList(String churchName, String eventName) {
        // 빈 linked list 생성
        List<ParticipantRegisterParam> params = new LinkedList<>();
        // 교회명과 종목명으로 불러온 applyment를 하나하나 ParticipantRegisterParam으로 변경
        for (Applyment applyment : applyRepo.findAllByEventNameAndParticipantChurchName(eventName, churchName)) {
            params.add(new ParticipantRegisterParam(applyment));
        }
        return params;
    }
}
