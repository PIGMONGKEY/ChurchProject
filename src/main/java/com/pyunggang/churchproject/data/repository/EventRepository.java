package com.pyunggang.churchproject.data.repository;

import com.pyunggang.churchproject.data.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, String> {
    Event findEventByNameIs(String name);
}
