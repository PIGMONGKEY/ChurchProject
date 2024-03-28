package com.pyunggang.churchproject.data.repository;

import com.pyunggang.churchproject.data.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Integer> {
    Participant findParticipantByName(String name);
}
