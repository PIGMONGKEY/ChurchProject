package com.pyunggang.churchproject.data.repository;

import com.pyunggang.churchproject.data.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, String> {
    Department findDepartmentByNameIs(String name);
}
