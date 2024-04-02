package com.pyunggang.churchproject.service;

import java.util.List;

public interface ChurchService {
    public List<String> findAllChurchNames();

    public boolean verifyPassword(String name, String password);

    public boolean saveChurch(String name);

    public String findChurchPassword(String churchName);

    public void deleteChurch(String churchName);
}
