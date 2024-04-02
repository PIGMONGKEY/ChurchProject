package com.pyunggang.churchproject.controller;

import com.pyunggang.churchproject.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/*")
public class AdminController {
    final private AdminService adminService;

    @GetMapping("/")
    public String admin(Model model) {
        model.addAttribute(adminService.getAdminPageInfo());

        return "/admin/admin";
    }
}
