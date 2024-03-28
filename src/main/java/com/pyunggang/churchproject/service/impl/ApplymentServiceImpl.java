package com.pyunggang.churchproject.service.impl;

import com.pyunggang.churchproject.data.dto.ParticipantRegisterParam;
import com.pyunggang.churchproject.data.entity.Participant;
import com.pyunggang.churchproject.data.repository.ApplymentRepository;
import com.pyunggang.churchproject.data.repository.ChurchRepository;
import com.pyunggang.churchproject.data.repository.DepartmentRepository;
import com.pyunggang.churchproject.data.repository.ParticipantRepository;
import com.pyunggang.churchproject.service.ApplymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplymentServiceImpl implements ApplymentService {
    final private ApplymentRepository applyRepo;
    final private ParticipantRepository partiRepo;
    final private ChurchRepository churchRepo;
    final private DepartmentRepository departRepo;

    /**
     * 참가자 정보 리스트로 받아서 insert
     * @param params List<ParticipantRegisterParam> 형태로 참가자 정보를 받음
     * @return 성공하면 true, 실패하면 false
     */
//    TODO: 동명이인 어떡할지 고민 필요
    @Override
    public boolean saveApplyment(List<ParticipantRegisterParam> params) {
        Participant participant;

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

            // 이미 등록된 참가자면, false 리턴
            if (applyRepo.existsByParticipant(param.getEventName(), participant)) {
                return false;
            } else {
                // DB 삽입 실패하면 false 리턴
                if (!partiRepo.save(participant).getName().equals(param.getName()))
                    return false;
            }
        }

        return true;
    }
}
