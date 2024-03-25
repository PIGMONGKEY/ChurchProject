package com.pyunggang.churchproject.service;

import java.util.List;

public interface ChurchService {
    public List<String> findAllChurchNames();

    public boolean verifyPassword(String password);

    public boolean saveChurch(String name);
}
