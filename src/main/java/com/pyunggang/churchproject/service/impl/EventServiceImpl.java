package com.pyunggang.churchproject.service.impl;

import com.pyunggang.churchproject.data.entity.Event;
import com.pyunggang.churchproject.data.repository.EventRepository;
import com.pyunggang.churchproject.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;

    @Override
    public List<String> findAllEventNames() {
        List<String> events = new LinkedList<>();
        for (Event event : eventRepository.findAll()) {
            events.add(event.getName());
        }

        return events;
    }

    @Override
    public boolean saveEvent(String eventName) {
        if (eventRepository.existsById(eventName))
            return false;

        eventRepository.save(Event.builder().name(eventName).build());

        return true;
    }
}
