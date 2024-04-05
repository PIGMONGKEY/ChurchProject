package com.pyunggang.churchproject.service;

import org.springframework.http.ResponseEntity;

public interface DepartmentService {
    public ResponseEntity addDepartment(String departmentName);
    public ResponseEntity deleteDepartment(String departmentName);
}
