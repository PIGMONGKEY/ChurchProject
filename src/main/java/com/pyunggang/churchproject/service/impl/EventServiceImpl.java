package com.pyunggang.churchproject.service.impl;

import com.pyunggang.churchproject.data.entity.Applyment;
import com.pyunggang.churchproject.data.entity.Event;
import com.pyunggang.churchproject.data.entity.Participant;
import com.pyunggang.churchproject.data.repository.ApplymentRepository;
import com.pyunggang.churchproject.data.repository.EventRepository;
import com.pyunggang.churchproject.data.repository.ParticipantRepository;
import com.pyunggang.churchproject.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public List<String> findAllEventNames() {
        List<String> events = new LinkedList<>();
        for (Event event : eventRepository.findAll()) {
            events.add(event.getName());
        }

        return events;
    }

    /**
     * 새로운 종목 추가
     * @param eventName 종목 이름
     */
    @Override
    public boolean saveEvent(String eventName) {
        // 이미 있는 종목이면 삽입하지 않음
        if (eventRepository.existsById(eventName))
            return false;

        eventRepository.save(Event.builder()
                                            .name(eventName)
                                            .build());

        return true;
    }

    /**
     * 종목 삭제
     * 종목 참가자 신청 정보 삭제
     * 참여 정보가 없는 참가자는 참가자 정보 삭제
     * @param eventName 종목 명
     * @return
     */
    @Override
    public boolean removeEvent(String eventName) {
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

        return true;
    }
}
