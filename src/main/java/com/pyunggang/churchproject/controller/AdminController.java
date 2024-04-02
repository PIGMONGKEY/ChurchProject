package com.pyunggang.churchproject.controller;

import com.pyunggang.churchproject.service.AdminService;
import com.pyunggang.churchproject.service.ChurchService;
import com.pyunggang.churchproject.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/*")
public class AdminController {
    final private AdminService adminService;
    final private ChurchService churchService;
    final private EventService eventService;


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

    @PostMapping("/newChurch")
    @ResponseBody
    public ResponseEntity addNewChurch(@RequestParam("newChurch") String churchName) {
        churchService.saveChurch(churchName);

        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/delete-church")
    @ResponseBody
    public ResponseEntity deleteChurch(@RequestParam("churchName") String churchName) {
        churchService.deleteChurch(churchName);

        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/newEvent")
    @ResponseBody
    public ResponseEntity addNewEvent(@RequestParam("newEvent") String eventName) {
        if (eventService.saveEvent(eventName)) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
