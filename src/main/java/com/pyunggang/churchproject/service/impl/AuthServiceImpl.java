package com.pyunggang.churchproject.service.impl;

import com.pyunggang.churchproject.data.dto.LoginParam;
import com.pyunggang.churchproject.data.dto.TokenInfoParam;
import com.pyunggang.churchproject.jwt.JwtTokenProvider;
import com.pyunggang.churchproject.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    final private JwtTokenProvider jwtTokenProvider;
    final private AuthenticationManagerBuilder authenticationManagerBuilder;
    final private RedisTemplate<String, Object> redisTemplate;

    @Override
    public ResponseEntity<TokenInfoParam> login(LoginParam loginParam, HttpServletResponse response) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginParam.getChurchName(), loginParam.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        TokenInfoParam tokenInfoParam = jwtTokenProvider.generateToken(authentication);

        Cookie cookie = new Cookie("refreshToken", tokenInfoParam.getRefreshToken());
        cookie.setMaxAge(60*60);
        cookie.setHttpOnly(true);

        tokenInfoParam.setRefreshToken("");
        response.addCookie(cookie);

        return new ResponseEntity<>(tokenInfoParam, HttpStatus.OK);
    }

    @Override
    public void logout(String refreshToken) {
        redisTemplate.opsForValue().set(refreshToken, "logout", jwtTokenProvider.getExpiration(refreshToken) - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public ResponseEntity<TokenInfoParam> refresh(String refreshToken, HttpServletResponse response) {
        TokenInfoParam tokenInfoParam = null;
        HttpStatus status = HttpStatus.OK;
        Cookie cookie;

        if (refreshToken != null && jwtTokenProvider.validateToken(refreshToken) && redisTemplate.opsForValue().get(refreshToken) == null) {
            tokenInfoParam = jwtTokenProvider.generateToken(jwtTokenProvider.getAuthentication(refreshToken));

            cookie = new Cookie("refreshToken", tokenInfoParam.getRefreshToken());
            cookie.setMaxAge(3*60*60);
            cookie.setHttpOnly(true);

            response.addCookie(cookie);

            tokenInfoParam.setRefreshToken("");
        } else {
            status = HttpStatus.UNAUTHORIZED;
        }

        return new ResponseEntity<>(tokenInfoParam, status);
    }
}
