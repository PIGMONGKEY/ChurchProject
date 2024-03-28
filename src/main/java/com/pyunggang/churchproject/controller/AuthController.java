package com.pyunggang.churchproject.controller;

import com.pyunggang.churchproject.data.dto.LoginParam;
import com.pyunggang.churchproject.service.ChurchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth/")
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
    public ResponseEntity<String> login(@RequestBody LoginParam loginParam) {
        String url;
        if (churchService.verifyPassword(loginParam.getChurchName(), loginParam.getPassword())) {
            url = "/register/home?churchName=" + loginParam.getChurchName();

            return new ResponseEntity<>(url, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
