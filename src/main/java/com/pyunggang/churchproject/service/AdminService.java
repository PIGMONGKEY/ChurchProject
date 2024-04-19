package com.pyunggang.churchproject.service;

import com.pyunggang.churchproject.data.dto.AdminPageParam;
import com.pyunggang.churchproject.data.dto.NotificationParam;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface AdminService {
    public AdminPageParam getAdminPageInfo();
    public void setNotification(NotificationParam notification);
    public ResponseEntity<NotificationParam> getNotification();
    public void deleteNotification();
    public void getAllInfoAsExcel(HttpServletResponse response) throws IOException;
}
