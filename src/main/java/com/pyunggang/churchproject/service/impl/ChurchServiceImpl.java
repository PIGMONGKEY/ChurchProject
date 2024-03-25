package com.pyunggang.churchproject.service.impl;

import com.pyunggang.churchproject.data.entity.Church;
import com.pyunggang.churchproject.data.repository.ChurchRepository;
import com.pyunggang.churchproject.service.ChurchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChurchServiceImpl implements ChurchService {
    final ChurchRepository churchRepository;

    /**
     * 저장된 모든 교회 이름을 ArrayList로 반환
     * @return ArrayList<String> - 교회 이름들
     */
    @Override
    public List<String> getAllChurchName() {
        List<String> churches = new ArrayList<>();
        for (Church church : churchRepository.findAll()) {
            churches.add(church.getName());
        }

        return churches;
    }

    @Override
    public boolean verifyPassword(String password) {
        return false;
    }
}
