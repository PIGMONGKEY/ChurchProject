package com.pyunggang.churchproject.controller;

import com.pyunggang.churchproject.service.ChurchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/register")
public class RegisterController {
    final ChurchService churchService;

    @GetMapping("/")
    public String registerHome(Model model) {
        model.addAttribute("churches", churchService.findAllChurches());

        return "/register/home";
    }
}
