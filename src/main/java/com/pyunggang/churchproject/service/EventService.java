package com.pyunggang.churchproject.service;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EventService {
    ResponseEntity<List<String>> findAllEventNames();

    ResponseEntity saveEvent(String eventName);

    ResponseEntity removeEvent(String eventName);
}
