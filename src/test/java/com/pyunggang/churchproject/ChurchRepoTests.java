package com.pyunggang.churchproject;

import com.pyunggang.churchproject.data.entity.Church;
import com.pyunggang.churchproject.data.repository.ChurchRepository;
import com.pyunggang.churchproject.service.ChurchService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class ChurchRepoTests {

    @Autowired
    ChurchRepository churchRepository;
    @Autowired
    ChurchService churchService;

//    @Test
//    public void findAllTest() {
//        List<Church> churches = churchRepository.findAll();
//
//        churches.forEach(church -> log.info(church.getChurch()));
//    }
//
//    @Test
//    public void getAllChurchesTest() {
//        churchRepository.findAllBy().forEach(church -> log.info(church.getChurch()));
//    }

//    @Test
//    public void getChurchByNameIsTest() {
//        log.info(churchRepository.findChurchByNameIs("평강").getPassword());
//    }

    @Test
    public void saveChurchTest() {
        String churches = "평강, 동도, 성현, 은석, 송우, 삼일, 광성, 영성, 제자들, 목동열방, 주님의, 장월, 늘품, 중앙단대, 참행복한, 가산제일, 분당중앙, 원당서문, 기쁨의, 꿈이있는, 시민의, 빛과소금, 금일";
        for (String church : churches.split(", ")) {
            churchService.saveChurch(church + "교회");
        }
    }
}
