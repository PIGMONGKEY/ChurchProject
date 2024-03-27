package com.pyunggang.churchproject.data.repository;

import com.pyunggang.churchproject.data.entity.Applyment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplymentRepository extends JpaRepository<Applyment, Integer> {
}
