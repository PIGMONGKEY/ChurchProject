package com.pyunggang.churchproject.controller;

import com.pyunggang.churchproject.data.entity.Church;
import com.pyunggang.churchproject.data.repository.ChurchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class ChurchController {
    final private ChurchRepository churchRepository;

    @PostMapping("/test")
    public void churchSave(@RequestBody Church church) {
        churchRepository.save(church);
    }
}
