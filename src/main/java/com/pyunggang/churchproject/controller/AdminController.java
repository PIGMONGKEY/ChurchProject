package com.pyunggang.churchproject.controller;

import ch.qos.logback.core.model.Model;
import com.pyunggang.churchproject.data.repository.ChurchRepository;
import com.pyunggang.churchproject.data.repository.DepartmentRepository;
import com.pyunggang.churchproject.data.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/*")
public class AdminController {
    final EventRepository eventRepo;
    final DepartmentRepository departRepo;
    final ChurchRepository churchRepo;

    @GetMapping("/")
    public String admin(Model model) {

        return "/admin/admin";
    }
}
