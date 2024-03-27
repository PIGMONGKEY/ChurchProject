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
import org.springframework.web.bind.annotation.RequestParam;

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

        return "register/home";
    }

    @GetMapping("/list")
    public String registerHome(@RequestParam("church") String church, @RequestParam("event") String event, Model model) {
        log.info(church + event);
        return "register/list";
    }
}
