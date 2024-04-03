package com.pyunggang.churchproject.service;

import com.pyunggang.churchproject.data.dto.LoginParam;
import com.pyunggang.churchproject.data.dto.TokenInfoParam;

import java.util.List;

public interface ChurchService {
    public List<String> findAllChurchNames();

    public boolean verifyPassword(String name, String password);

    public boolean saveChurch(String name);

    public String findChurchPassword(String churchName);

    public void deleteChurch(String churchName);

    public TokenInfoParam login(LoginParam loginParam);
}
