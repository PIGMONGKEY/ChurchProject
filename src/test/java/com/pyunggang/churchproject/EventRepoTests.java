package com.pyunggang.churchproject;

import com.pyunggang.churchproject.data.entity.Event;
import com.pyunggang.churchproject.data.repository.EventRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
@Slf4j
public class EventRepoTests {
    @Autowired
    EventRepository eventRepository;

    @Test
    public void saveTest() {
        String[] events = {"글짓기", "그림그리기", "독창", "합창", "율동", "암송"};

        for (String event : events) {
            eventRepository.save(Event.builder().name(event).build());
        }
    }
}
