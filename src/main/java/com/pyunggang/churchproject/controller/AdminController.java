package com.pyunggang.churchproject.controller;

import com.pyunggang.churchproject.service.AdminService;
import com.pyunggang.churchproject.service.ChurchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/*")
public class AdminController {
    final private AdminService adminService;
    final private ChurchService churchService;


    @GetMapping("/")
    public String admin(Model model) {
        model.addAttribute(adminService.getAdminPageInfo());

        return "/admin/admin";
    }

    @GetMapping("/church-password")
    @ResponseBody
    public ResponseEntity<String> getChurchPassword(@RequestParam("churchName") String churchName) {
        String password = churchService.findChurchPassword(churchName);

        return new ResponseEntity<>(password, HttpStatus.OK);
    }
}
