package com.pyunggang.churchproject.service.impl;

import com.pyunggang.churchproject.data.entity.Department;
import com.pyunggang.churchproject.data.entity.Participant;
import com.pyunggang.churchproject.data.repository.ApplymentRepository;
import com.pyunggang.churchproject.data.repository.DepartmentRepository;
import com.pyunggang.churchproject.data.repository.ParticipantRepository;
import com.pyunggang.churchproject.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    final private DepartmentRepository departmentRepository;
    final private ParticipantRepository participantRepository;
    final private ApplymentRepository applymentRepository;

    @Override
    public ResponseEntity addDepartment(String departmentName) {
        HttpStatus status = HttpStatus.OK;

        if (departmentRepository.existsById(departmentName))
            status = HttpStatus.BAD_REQUEST;
        else {
            Department department = Department.builder().name(departmentName).build();

            if (!departmentRepository.save(department).equals(department))
                status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity(status);
    }

    @Override
    public ResponseEntity deleteDepartment(String departmentName) {
        Department department = departmentRepository.findDepartmentByNameIs(departmentName);
        List<Participant> participants = participantRepository.findAllByDepartmentName(departmentName);

        if (!participants.isEmpty()) {
            for (Participant participant : participants) {
                applymentRepository.deleteAll(applymentRepository.findAllByParticipant(participant));
                participantRepository.delete(participant);
            }
        }

        departmentRepository.delete(department);

        return new ResponseEntity(HttpStatus.OK);
    }
}
