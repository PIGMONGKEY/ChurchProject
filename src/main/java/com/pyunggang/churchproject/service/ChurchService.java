package com.pyunggang.churchproject.service;

import com.pyunggang.churchproject.data.dto.LoginParam;
import com.pyunggang.churchproject.data.dto.TokenInfoParam;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ChurchService {
    public ResponseEntity<List<String>> findAllChurchNames();

    public ResponseEntity saveChurch(String name);

    public ResponseEntity<String> findChurchPassword(String churchName);

    public ResponseEntity deleteChurch(String churchName);

    public ResponseEntity<TokenInfoParam> login(LoginParam loginParam);
}
