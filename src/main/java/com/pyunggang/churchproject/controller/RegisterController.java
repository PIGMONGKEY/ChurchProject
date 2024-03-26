package com.pyunggang.churchproject.controller;

import com.pyunggang.churchproject.data.dto.LoginParam;
import com.pyunggang.churchproject.data.repository.EventRepository;
import com.pyunggang.churchproject.service.ChurchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/register/*")
public class RegisterController {
    final ChurchService churchService;
    final EventRepository eventRepository;

    @GetMapping("/home")
    public String registerHome(Model model) {
        model.addAttribute("churches", churchService.findAllChurchNames());
        model.addAttribute("events", eventRepository.findAll());
        model.addAttribute("loginParam", new LoginParam());

        return "/register/home";
    }

    @PostMapping("/list")
    public String registerHome(LoginParam loginParam, Model model) {

        return "/register/home";
    }
}
