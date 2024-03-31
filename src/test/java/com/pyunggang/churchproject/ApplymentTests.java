package com.pyunggang.churchproject;

import com.pyunggang.churchproject.data.repository.ApplymentRepository;
import com.pyunggang.churchproject.data.repository.ParticipantRepository;
import com.pyunggang.churchproject.service.ApplymentService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class ApplymentTests {
    @Autowired
    ApplymentRepository applymentRepository;
    @Autowired
    ApplymentService applymentService;
    @Autowired
    ParticipantRepository participantRepository;

//    @Test
//    public void findAllTest() {
//        applymentRepository.findAll().forEach(applyment -> log.info(applyment.getParticipant().getName()));
//    }

    @Test
    public void findOneTest() {
//        log.info(applymentRepository.findApplymentByEventNameAndParticipantNameAndParticipantAgeAndParticipantGradeAndParticipantGenderAndParticipantChurchName(
//                "글짓기",
//                "김기리",
//                12,
//                5,
//                "male",
//                "평강교회"
//        ).getParticipant().getName());
//        log.info(applymentRepository.findApplymentByParticipant(participantRepository.findById(1).get()).getEpNo() + "");
    }

    @Test
    public void findAllTest() {
        applymentService.findApplymentList("평강교회", "글짓기");
    }

    @Test
    public void deleteTest() {
        applymentService.deleteApplyment("글짓기", 2);
    }
}
