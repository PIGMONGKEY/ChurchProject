package com.pyunggang.churchproject.controller;

import com.pyunggang.churchproject.data.dto.LoginParam;
import com.pyunggang.churchproject.data.dto.TokenInfoParam;
import com.pyunggang.churchproject.service.AuthService;
import com.pyunggang.churchproject.service.ChurchService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class AuthController {
    final private ChurchService churchService;
    final private AuthService authService;

    /**
     * 교회 로그인 RestApi
     * @param loginParam LoginParam 형태로 데이터를 받아서 비밀번호 확인 후 결과를 리턴함
     * @return
     */
    @PostMapping("login")
    @ResponseBody
    public ResponseEntity<TokenInfoParam> login(@RequestBody LoginParam loginParam, HttpServletResponse response) {
        return authService.login(loginParam, response);
    }

    /**
     * 로그인 페이지로 이동
     */
    @GetMapping("login")
    public String loginPage(Model model) {
        model.addAttribute("churches", churchService.findAllChurchNames().getBody());
        model.addAttribute("loginParam", new LoginParam());

        return "login";
    }

    @PostMapping("auth/logout")
    @ResponseBody
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public void logout(HttpServletRequest request) {
        String refreshToken = null;

        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals("refreshToken")) {
                refreshToken = cookie.getValue();
                break;
            }
        }

        authService.logout(refreshToken);
    }

    @PostMapping("refresh")
    @ResponseBody
    public ResponseEntity<TokenInfoParam> refresh(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = null;

        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals("refreshToken")) {
                refreshToken = cookie.getValue();
                break;
            }
        }

        return authService.refresh(refreshToken, response);
    }
}
