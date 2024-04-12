package com.pyunggang.churchproject.service.impl;

import com.pyunggang.churchproject.data.entity.Applyment;
import com.pyunggang.churchproject.data.entity.Church;
import com.pyunggang.churchproject.data.entity.Participant;
import com.pyunggang.churchproject.data.repository.ApplymentRepository;
import com.pyunggang.churchproject.data.repository.ChurchRepository;
import com.pyunggang.churchproject.data.repository.ParticipantRepository;
import com.pyunggang.churchproject.service.ChurchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ChurchServiceImpl implements ChurchService {
    final private ChurchRepository churchRepository;
    final private ParticipantRepository participantRepository;
    final private ApplymentRepository applymentRepository;

    /**
     * 저장된 모든 교회 이름을 ArrayList로 반환
     * @return ArrayList<String> - 교회 이름들
     */
    @Override
    @Transactional
    public ResponseEntity<List<String>> findAllChurchNames() {
        HttpStatus status = HttpStatus.OK;
        List<String> churchNames = new ArrayList<>();
        List<Church> churches = churchRepository.findAll();

        if (churches.isEmpty())
            status = HttpStatus.NOT_FOUND;
        else
            for (Church church : churches)
                churchNames.add(church.getName());

        return new ResponseEntity<>(churchNames, status);
    }

    // 새로운 교회 추가
    // 비밀번호는 4자리 숫자 랜덤값
    // 이미 있으면 400 리턴
    @Override
    @Transactional
    public ResponseEntity saveChurch(String name) {
        HttpStatus status = HttpStatus.OK;
        Random random = new Random(System.currentTimeMillis());
        String password;
        Church church;

        if (churchRepository.existsById(name))
            status = HttpStatus.BAD_REQUEST;
        else {
            random.setSeed(System.currentTimeMillis());
            password = Integer.toString(random.nextInt(1000, 10000));

            church = Church.builder()
                                    .name(name)
                                    .password(password)
                                    .role("USER")
                                    .build();

            if (!churchRepository.save(church).getName().equals(name))
                status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity(status);
    }

    // 교회 비밀번호 검색
    @Override
    @Transactional
    public ResponseEntity<String> findChurchPassword(String churchName) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        Church church = churchRepository.findById(churchName).get();
        String password = null;

        if (church != null) {
            status = HttpStatus.OK;
            password = church.getPassword();
        }

        return new ResponseEntity<>(password, status);
    }

    // 교회 삭제
    // 교회 신청 내역, 참가자 모두 삭제
    @Override
    public ResponseEntity deleteChurch(String churchName) {
        HttpStatus status = HttpStatus.OK;
        Church church = churchRepository.findById(churchName).get();
        List<Applyment> applyments = applymentRepository.findAllByParticipantChurchName(churchName);
        List<Participant> participants = participantRepository.findAllByChurchName(churchName);

        if (!applyments.isEmpty()) {
            applymentRepository.deleteAll(applyments);
            participantRepository.deleteAll(participants);
        }

        churchRepository.delete(church);

        return new ResponseEntity(status);
    }
}
