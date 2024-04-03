package com.pyunggang.churchproject.service.impl;

import com.pyunggang.churchproject.data.dto.LoginParam;
import com.pyunggang.churchproject.data.dto.TokenInfoParam;
import com.pyunggang.churchproject.data.entity.Applyment;
import com.pyunggang.churchproject.data.entity.Church;
import com.pyunggang.churchproject.data.entity.Participant;
import com.pyunggang.churchproject.data.repository.ApplymentRepository;
import com.pyunggang.churchproject.data.repository.ChurchRepository;
import com.pyunggang.churchproject.data.repository.ParticipantRepository;
import com.pyunggang.churchproject.jwt.JwtTokenProvider;
import com.pyunggang.churchproject.service.ChurchService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ChurchServiceImpl implements ChurchService {
    final private ChurchRepository churchRepository;
    final private ParticipantRepository participantRepository;
    final private ApplymentRepository applymentRepository;
    final private JwtTokenProvider jwtTokenProvider;
    final private AuthenticationManagerBuilder authenticationManagerBuilder;

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
        if (churchRepository.existsById(name)) {
            return false;
        }

        String password;

        random.setSeed(System.currentTimeMillis());
        password = Integer.toString(random.nextInt(1000, 10000));

        Church returnChurch = churchRepository.saveAndFlush(Church
                .builder()
                .name(name)
                .password(password)
                .role("USER")
                .build());

        return returnChurch.getName().equals(name) ? true : false;
    }

    @Override
    public String findChurchPassword(String churchName) {
        return churchRepository.findChurchByNameIs(churchName).getPassword();
    }

    @Override
    public void deleteChurch(String churchName) {
        Church church = churchRepository.findChurchByNameIs(churchName);
        List<Applyment> applyments = applymentRepository.findAllByParticipantChurchName(churchName);
        List<Participant> participants = participantRepository.findAllByChurchName(churchName);

        if (!applyments.isEmpty()) {
            applymentRepository.deleteAll(applyments);
            participantRepository.deleteAll(participants);
        }

        churchRepository.delete(church);
    }

    @Override
    public TokenInfoParam login(LoginParam loginParam) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginParam.getChurchName(), loginParam.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        TokenInfoParam tokenInfoParam = jwtTokenProvider.generateToken(authentication);

        return tokenInfoParam;
    }
}
