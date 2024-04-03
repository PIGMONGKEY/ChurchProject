package com.pyunggang.churchproject.service;

import com.pyunggang.churchproject.data.entity.Church;
import com.pyunggang.churchproject.data.repository.ChurchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    final private ChurchRepository churchRepository;
    final private PasswordEncoder passwordEncoder;
    @Value("${admin.id}")
    private String adminId;
    @Value("${admin.password}")
    private String adminPassword;
    @Value("${admin.role}")
    private String adminRole;

    /**
     * 교회 이름으로 교회 정보 검색하여 UserDetails 객체 반환
     * admin 계정 로그인 처리 해줌
     * @param username the username identifying the user whose data is required.
     * @throws UsernameNotFoundException : 교회 정보 없음
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username.equals(adminId)) {
            return User.builder()
                    .username(adminId)
                    .password(passwordEncoder.encode(adminPassword))
                    .roles(adminRole)
                    .build();
        } else {
            return churchRepository.findById(username)
                    .map(this::createUserDetails)
                    .orElseThrow(() -> new UsernameNotFoundException("등록되지 않은 교회입니다."));

        }
    }

    /**
     * 교회 정보로 UserDetails 객체 생성하여 반환
     * @param church 교회 정보 객체
     * @return UserDetails 객체
     */
    private UserDetails createUserDetails(Church church) {
        return User.builder()
                .username(church.getName())
                .password(passwordEncoder.encode(church.getPassword()))
                .roles(church.getRole())
                .build();
    }
}
