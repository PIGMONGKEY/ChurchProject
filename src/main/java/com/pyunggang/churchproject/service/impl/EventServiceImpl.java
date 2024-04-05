package com.pyunggang.churchproject.service.impl;

import com.pyunggang.churchproject.data.entity.Applyment;
import com.pyunggang.churchproject.data.entity.Event;
import com.pyunggang.churchproject.data.entity.Participant;
import com.pyunggang.churchproject.data.repository.ApplymentRepository;
import com.pyunggang.churchproject.data.repository.EventRepository;
import com.pyunggang.churchproject.data.repository.ParticipantRepository;
import com.pyunggang.churchproject.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    final private EventRepository eventRepository;
    final private ApplymentRepository applymentRepository;
    final private ParticipantRepository participantRepository;

    /**
     * 모든 종목 이름 가져오기
     */
    @Override
    @Transactional
    public ResponseEntity<List<String>> findAllEventNames() {
        HttpStatus status = HttpStatus.OK;
        List<String> eventNames = new LinkedList<>();
        List<Event> events = eventRepository.findAll();

        if (events.isEmpty())
            status = HttpStatus.BAD_REQUEST;
        else
            for (Event event : events)
                eventNames.add(event.getName());

        return new ResponseEntity<>(eventNames, status);
    }

    /**
     * 새로운 종목 추가
     * @param eventName 종목 이름
     */
    @Override
    @Transactional
    public ResponseEntity saveEvent(String eventName) {
        HttpStatus status = HttpStatus.OK;
        // 이미 있는 종목이면 삽입하지 않음
        if (eventRepository.existsById(eventName))
            status = HttpStatus.BAD_REQUEST;
        else
            eventRepository.save(Event.builder().name(eventName).build());

        return new ResponseEntity(status);
    }

    /**
     * 종목 삭제
     * 종목 참가자 신청 정보 삭제
     * 참여 정보가 없는 참가자는 참가자 정보 삭제
     * @param eventName 종목 명
     * @return
     */
    @Override
    public ResponseEntity removeEvent(String eventName) {
        HttpStatus status = HttpStatus.OK;
        Event event = eventRepository.findById(eventName).get();
        List<Applyment> applyments = applymentRepository.findAllByEventName(eventName);

        // 종목 신청 정보가 있다면 신청 정보 삭제
        if (!applyments.isEmpty()) {
            applymentRepository.deleteAll(applyments);
            // 다른 종목에 참여하지 않는 참가자 정보 삭제
            for (Applyment applyment : applyments) {
                if (!applymentRepository.existsByParticipant(applyment.getParticipant())) {
                    participantRepository.delete(applyment.getParticipant());
                }
            }
        }

        eventRepository.delete(event);

        return new ResponseEntity(status);
    }
}
