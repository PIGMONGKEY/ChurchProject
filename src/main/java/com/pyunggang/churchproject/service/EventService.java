package com.pyunggang.churchproject.service;

import java.util.List;

public interface EventService {
    List<String> findAllEventNames();

    boolean saveEvent(String eventName);

    boolean removeEvent(String eventName);
}
