package com.pyunggang.churchproject.service.impl;

import com.pyunggang.churchproject.data.entity.Department;
import com.pyunggang.churchproject.data.entity.Participant;
import com.pyunggang.churchproject.data.repository.ApplymentRepository;
import com.pyunggang.churchproject.data.repository.DepartmentRepository;
import com.pyunggang.churchproject.data.repository.ParticipantRepository;
import com.pyunggang.churchproject.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    final private DepartmentRepository departmentRepository;
    final private ParticipantRepository participantRepository;
    final private ApplymentRepository applymentRepository;

    @Override
    public boolean addDepartment(String departmentName) {
        if (departmentRepository.existsById(departmentName))
            return false;

        Department department = Department.builder()
                                                    .name(departmentName)
                                                    .build();

        if (!departmentRepository.save(department).equals(department))
            return false;

        return true;
    }

    @Override
    public boolean deleteDepartment(String departmentName) {
        Department department = departmentRepository.findDepartmentByNameIs(departmentName);
        List<Participant> participants = participantRepository.findAllByDepartmentName(departmentName);

        if (!participants.isEmpty()) {
            for (Participant participant : participants) {
                applymentRepository.deleteAll(applymentRepository.findAllByParticipant(participant));
                participantRepository.delete(participant);
            }
        }

        departmentRepository.delete(department);

        return true;
    }
}
