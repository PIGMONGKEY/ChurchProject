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
        String[] churches = {"평강교회", "동도교회", "순복음교회", "영훈오륜교회", "새소망교회", "뉴비젼교회", "광명교회", "빛과소금교회", "사랑의교회"};

        for (String church : churches) {
            churchService.saveChurch(church);
        }
    }
}
