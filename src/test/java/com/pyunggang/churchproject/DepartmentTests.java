package com.pyunggang.churchproject;

import com.pyunggang.churchproject.data.entity.Department;
import com.pyunggang.churchproject.data.repository.DepartmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class DepartmentTests {
    @Autowired
    private DepartmentRepository departmentRepository;

    @Test
    public void saveTest() {
        String[] names = {"영아부", "유치부", "유년부", "초등부", "중등부", "고등부"};
        int[] mins = {0, 5, 8, 11, 14, 17};
        int[] maxes = {4, 7, 10, 13, 16, 19};

        for (int i=0; i<names.length; i++) {
            departmentRepository.save(Department.builder()
                            .name(names[i])
                            .minAge(mins[i])
                            .maxAge(maxes[i])
                            .build());
        }
    }
}
