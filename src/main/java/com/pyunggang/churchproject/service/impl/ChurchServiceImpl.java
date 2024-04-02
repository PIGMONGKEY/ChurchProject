package com.pyunggang.churchproject.service.impl;

import com.pyunggang.churchproject.data.entity.Church;
import com.pyunggang.churchproject.data.repository.ChurchRepository;
import com.pyunggang.churchproject.service.ChurchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ChurchServiceImpl implements ChurchService {
    final ChurchRepository churchRepository;
    final Random random = new Random();

    /**
     * 저장된 모든 교회 이름을 ArrayList로 반환
     * @return ArrayList<String> - 교회 이름들
     */
    @Override
    public List<String> findAllChurchNames() {
        List<String> churches = new ArrayList<>();
        for (Church church : churchRepository.findAll()) {
            churches.add(church.getName());
        }

        return churches;
    }

    @Override
    public boolean verifyPassword(String name, String password) {
        return churchRepository.findChurchByNameIs(name).getPassword().equals(password);
    }

    @Override
    public boolean saveChurch(String name) {
        String password;

        random.setSeed(System.currentTimeMillis());
        password = Integer.toString(random.nextInt(1000, 10000));

        Church returnChurch = churchRepository.saveAndFlush(Church
                .builder()
                .name(name)
                .password(password)
                .build());

        return returnChurch.getName().equals(name) ? true : false;
    }

    @Override
    public String findChurchPassword(String churchName) {
        return churchRepository.findChurchByNameIs(churchName).getPassword();
    }
}
