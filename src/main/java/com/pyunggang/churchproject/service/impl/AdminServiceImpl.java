package com.pyunggang.churchproject.service.impl;

import com.pyunggang.churchproject.data.dto.AdminPageParam;
import com.pyunggang.churchproject.data.repository.ChurchRepository;
import com.pyunggang.churchproject.data.repository.DepartmentRepository;
import com.pyunggang.churchproject.data.repository.EventRepository;
import com.pyunggang.churchproject.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    final private ChurchRepository churchRepository;
    final private EventRepository eventRepository;
    final private DepartmentRepository departmentRepository;

    @Override
    public AdminPageParam getAdminPageInfo() {
        AdminPageParam adminPageParam = AdminPageParam.builder()
                .churches(churchRepository.findAll())
                .departments(departmentRepository.findAll())
                .events(eventRepository.findAll())
                .build();

        return adminPageParam;
    }
}
