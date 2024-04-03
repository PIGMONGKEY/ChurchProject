package com.pyunggang.churchproject.service;

import com.pyunggang.churchproject.data.dto.AdminPageParam;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface AdminService {
    public AdminPageParam getAdminPageInfo();
    public void  getAllInfoAsExcel(HttpServletResponse response) throws IOException;
}
