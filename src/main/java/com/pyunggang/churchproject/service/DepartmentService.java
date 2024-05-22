package com.pyunggang.churchproject.service;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DepartmentService {
    public ResponseEntity addDepartment(String departmentName);
    public ResponseEntity deleteDepartment(String departmentName);
    public List<String> getAllDepartment();
}
