package com.pyunggang.churchproject.service;

import com.pyunggang.churchproject.data.dto.LoginParam;
import com.pyunggang.churchproject.data.dto.TokenInfoParam;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    public ResponseEntity<TokenInfoParam> login(LoginParam loginParam, HttpServletResponse response);

    public void logout(String token);

    public ResponseEntity<TokenInfoParam> refresh(String refreshToken, HttpServletResponse response);
}
