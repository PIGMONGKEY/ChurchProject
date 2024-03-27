package com.pyunggang.churchproject;

import com.pyunggang.churchproject.data.entity.*;
import com.pyunggang.churchproject.data.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class ParticipantTests {
    @Autowired
    private ParticipantRepository participantRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private ChurchRepository churchRepository;
    @Autowired
    private ApplymentRepository applymentRepository;

    @Test
    public void saveTest() {
        Participant participant = Participant
                .builder()
                .name("이원희")
                .age(15)
                .grade(2)
                .gender("male")
                .church(churchRepository.findChurchByNameIs("평강교회"))
                .department(departmentRepository.findDepartmentByNameIs("중등부"))
                .build();
        participantRepository.save(participant);

        applymentRepository.save(Applyment
                .builder()
                .participant(participant)
                .event(eventRepository.findEventByNameIs("글짓기"))
                .build());
    }
}
