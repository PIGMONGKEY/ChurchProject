package com.pyunggang.churchproject.controller;

import com.pyunggang.churchproject.data.dto.DeleteParam;
import com.pyunggang.churchproject.data.dto.LoginParam;
import com.pyunggang.churchproject.data.dto.ApplymentParam;
import com.pyunggang.churchproject.data.dto.RequestApplymentListParam;
import com.pyunggang.churchproject.service.ApplymentService;
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
public class ApplymentController {
    final ChurchService churchService;
    final EventService eventService;
    final ApplymentService applymentService;

    /**
     * 로그인 페이지로 이동
     */
    @GetMapping("/start")
    public String registerHome(Model model) {
        model.addAttribute("churches", churchService.findAllChurchNames());
        model.addAttribute("loginParam", new LoginParam());

        return "/login";
    }

    /**
     * 신청 홈으로 이동
     * @param churchName 교회 이름을 받아, 교회 홈으로 이동시킴
     */
    @GetMapping("/home")
    public String registerHome(@RequestParam("churchName") String churchName, Model model) {
        model.addAttribute("events", eventService.findAllEventNames());
        model.addAttribute("churchName", churchName);

        return "/applyment/home";
    }

    /**
     * 참가자 신청 페이지로 이동
     * 교회명과 종목명을 넘겨줌
     * @param churchName 교회 이름
     * @param eventName 종목 이름
     */
    @GetMapping("/register")
    public String registerParticipant(@RequestParam("churchName") String churchName, @RequestParam("eventName") String eventName, Model model) {
        model.addAttribute("church", churchName);
        model.addAttribute("event", eventName);

        return "/applyment/register";
    }

    /**
     * 참가지 신청 RestApi
     * @param applymentParams ParticipantRegisterParam list 형태로 받음
     * @return
     */
    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity registerParticipant(@RequestBody List<ApplymentParam> applymentParams) {
        if(applymentService.saveApplyment(applymentParams))
            return new ResponseEntity(HttpStatus.OK);
        else
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 신청 정보 리스트 요청 API
     * @param param RequestApplymentListParam - eventName / churchName
     * @return ApplymentParam - List 형태로 반환
     */
    @PostMapping("/list")
    @ResponseBody
    public ResponseEntity<List<ApplymentParam>> applymentList(@RequestBody RequestApplymentListParam param) {
        return new ResponseEntity<>(applymentService.findApplymentList(param.getChurchName(), param.getEventName()), HttpStatus.OK);
    }

    /**
     * 신청 정보 삭제 API
     * @param deleteParam eventName, participantId
     * @return
     */
    @PostMapping("/delete")
    @ResponseBody
    public ResponseEntity deleteApplyment(@RequestBody DeleteParam deleteParam) {
        applymentService.deleteApplyment(deleteParam.getEventName(), deleteParam.getParticipantId());

        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 신청 정보 수정 API
     * @param applymentParam
     * @return
     */
    @PostMapping("/modify")
    @ResponseBody
    public ResponseEntity modifyApplyment(@RequestBody ApplymentParam applymentParam) {
        applymentService.updateApplyment(applymentParam);

        return new ResponseEntity(HttpStatus.OK);
    }
}
