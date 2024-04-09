package com.pyunggang.churchproject;

import com.pyunggang.churchproject.data.dto.ApplymentParam;
import com.pyunggang.churchproject.data.entity.Participant;
import com.pyunggang.churchproject.service.AdminService;
import com.pyunggang.churchproject.service.ApplymentService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
@Slf4j
public class TotalTests {
    @Autowired
    private ApplymentService applymentService;
    @Autowired
    private AdminService adminService;

    @Test
    public void deadLockTest() throws InterruptedException{
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        CountDownLatch latch = new CountDownLatch(5);

        List<ApplymentParam> applymentParams1 = new ArrayList<>();
        List<ApplymentParam> applymentParams2 = new ArrayList<>();
        List<ApplymentParam> applymentParams3 = new ArrayList<>();
        List<ApplymentParam> applymentParams4 = new ArrayList<>();
        List<ApplymentParam> applymentParams5 = new ArrayList<>();

        for (int i=1; i<20; i++) {
            applymentParams1.add(ApplymentParam.builder()
                    .name(i+"")
                    .age(i)
                    .grade(i)
                    .gender("male")
                    .department("유년부")
                    .eventName("글짓기")
                    .churchName("가산제일교회")
                    .build());
        }

        for (int i=1; i<20; i++) {
            applymentParams2.add(ApplymentParam.builder()
                    .name(i+"")
                    .age(i)
                    .grade(i)
                    .gender("male")
                    .department("유년부")
                    .eventName("글짓기")
                    .churchName("평강교회")
                    .build());
        }

        for (int i=1; i<20; i++) {
            applymentParams3.add(ApplymentParam.builder()
                    .name(i+"")
                    .age(i)
                    .grade(i)
                    .gender("male")
                    .department("유년부")
                    .eventName("글짓기")
                    .churchName("은석교회")
                    .build());
        }

        for (int i=1; i<20; i++) {
            applymentParams4.add(ApplymentParam.builder()
                    .name(i+"")
                    .age(i)
                    .grade(i)
                    .gender("male")
                    .department("유년부")
                    .eventName("글짓기")
                    .churchName("성현교회")
                    .build());
        }

        for (int i=1; i<20; i++) {
            applymentParams5.add(ApplymentParam.builder()
                    .name(i+"")
                    .age(i)
                    .grade(i)
                    .gender("male")
                    .department("유년부")
                    .eventName("글짓기")
                    .churchName("동도교회")
                    .build());
        }

        log.info("동시성 테스트 시작");

        executorService.execute(() -> {
            applymentService.saveApplyment(applymentParams1);
            latch.countDown();
        });
        executorService.execute(() -> {
            applymentService.saveApplyment(applymentParams2);
            latch.countDown();
        });
        executorService.execute(() -> {
            applymentService.saveApplyment(applymentParams3);
            latch.countDown();
        });
        executorService.execute(() -> {
            applymentService.saveApplyment(applymentParams4);
            latch.countDown();
        });
        executorService.execute(() -> {
            applymentService.saveApplyment(applymentParams5);
            latch.countDown();
        });
        latch.await();
    }
}
