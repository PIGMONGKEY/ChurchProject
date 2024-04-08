package com.pyunggang.churchproject.config;

import com.pyunggang.churchproject.jwt.JwtAuthenticationFilter;
import com.pyunggang.churchproject.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    final private JwtTokenProvider jwtTokenProvider;
    final private RedisTemplate<String, Object> redisTemplate;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement((sessionManager) -> sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((authorizeRequest) -> {
                    // 교회 홈 접근
                    authorizeRequest.requestMatchers("/register/home").permitAll();
                    // 교회 참가자 신청 접근
                    authorizeRequest.requestMatchers("/register/register").permitAll();
                    // 로그인 페이지 접근
                    authorizeRequest.requestMatchers("/login").permitAll();
                    // 관리자 로그인 페이지 접근
                    authorizeRequest.requestMatchers("/admin/login").permitAll();
                    // 관리자 페이지 접근
                    authorizeRequest.requestMatchers("/admin/").permitAll();
                    // 엑셀 다운로드 링크 접근
                    authorizeRequest.requestMatchers("/admin/excel-download").permitAll();
                    // 나머지 모두 인증 필요
                    authorizeRequest.anyRequest().authenticated();
                })
                // jwt 토큰 필터 적용
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, redisTemplate), UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
