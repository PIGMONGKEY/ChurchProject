package com.pyunggang.churchproject.controller;

import com.pyunggang.churchproject.data.dto.LoginParam;
import com.pyunggang.churchproject.data.dto.ParticipantRegisterParam;
import com.pyunggang.churchproject.service.ChurchService;
import com.pyunggang.churchproject.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/register/*")
public class RegisterController {
    final ChurchService churchService;
    final EventService eventService;

    @GetMapping("/start")
    public String registerHome(Model model) {
        model.addAttribute("churches", churchService.findAllChurchNames());
        model.addAttribute("loginParam", new LoginParam());

        return "/register/start";
    }

    @GetMapping("/home")
    public String registerHome(@RequestParam("churchName") String churchName, Model model) {
        model.addAttribute("events", eventService.findAllEventNames());
        model.addAttribute("churchName", churchName);

        return "/register/home";
    }

    @GetMapping("/register")
    public String registerParticipant(@RequestParam("churchName") String churchName, @RequestParam("eventName") String eventName, Model model) {
        model.addAttribute("church", churchName);
        model.addAttribute("event", eventName);

        return "/register/register";
    }

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity registerParticipant(@RequestBody List<ParticipantRegisterParam> participants) {
        participants.forEach(participantRegisterParam -> log.info(participantRegisterParam.getName()));

        return new ResponseEntity(HttpStatus.OK);
    }
}
