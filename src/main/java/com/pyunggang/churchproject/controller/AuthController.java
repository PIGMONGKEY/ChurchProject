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

    @PostMapping("login")
    @ResponseBody
    public ResponseEntity<String> login(@RequestBody LoginParam loginParam) {
        String url;
        if (churchService.verifyPassword(loginParam.getChurchName(), loginParam.getPassword())) {
            url = "/register/list?church=" + loginParam.getChurchName();
            return new ResponseEntity<>(url, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
