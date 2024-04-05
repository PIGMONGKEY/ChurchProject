package com.pyunggang.churchproject.controller;

import com.pyunggang.churchproject.data.dto.LoginParam;
import com.pyunggang.churchproject.data.dto.TokenInfoParam;
import com.pyunggang.churchproject.service.ChurchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class AuthController {
    final ChurchService churchService;

    /**
     * 교회 로그인 RestApi
     * @param loginParam LoginParam 형태로 데이터를 받아서 비밀번호 확인 후 결과를 리턴함
     * @return
     */
    @PostMapping("login")
    @ResponseBody
    public ResponseEntity<TokenInfoParam> login(@RequestBody LoginParam loginParam) {
        return churchService.login(loginParam);
    }

    /**
     * 로그인 페이지로 이동
     */
    @GetMapping("login")
    public String loginPage(Model model) {
        model.addAttribute("churches", churchService.findAllChurchNames());
        model.addAttribute("loginParam", new LoginParam());

        return "login";
    }
}
