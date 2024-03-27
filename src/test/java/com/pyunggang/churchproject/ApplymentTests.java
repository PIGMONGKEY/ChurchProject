package com.pyunggang.churchproject;

import com.pyunggang.churchproject.data.repository.ApplymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class ApplymentTests {
    @Autowired
    ApplymentRepository applymentRepository;

    @Test
    public void findAllTest() {
        applymentRepository.findAll().forEach(applyment -> log.info(applyment.getParticipant().getName()));
    }
}
