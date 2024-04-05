package com.pyunggang.churchproject.data.repository;

import com.pyunggang.churchproject.data.entity.Church;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChurchRepository extends JpaRepository<Church, String> {
}
