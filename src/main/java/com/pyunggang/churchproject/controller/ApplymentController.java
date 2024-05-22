package com.pyunggang.churchproject.controller;

import com.pyunggang.churchproject.data.dto.DeleteParam;
import com.pyunggang.churchproject.data.dto.ApplymentParam;
import com.pyunggang.churchproject.data.dto.RequestApplymentListParam;
import com.pyunggang.churchproject.service.ApplymentService;
import com.pyunggang.churchproject.service.ChurchService;
import com.pyunggang.churchproject.service.DepartmentService;
import com.pyunggang.churchproject.service.EventService;
import com.pyunggang.churchproject.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    final DepartmentService departmentService;

    // 교회 홈
    @GetMapping("home")
    public String registerHome(Model model) {
        model.addAttribute("events", eventService.findAllEventNames().getBody());
        return "applyment/home";
    }

    // 로그인한 교회 이름 API
    @GetMapping("church")
    @ResponseBody
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> getChurchName() {
        return new ResponseEntity<>(SecurityUtil.getCurrentChurchName(), HttpStatus.OK);
    }

    /**
     * 참가자 신청 페이지로 이동
     * 교회명과 종목명을 넘겨줌
     * @param churchName 교회 이름
     * @param eventName 종목 이름
     */
    @GetMapping("register")
    public String registerParticipant(@RequestParam("churchName") String churchName, @RequestParam("eventName") String eventName, Model model) {
        model.addAttribute("church", churchName);
        model.addAttribute("event", eventName);
        model.addAttribute("departments", departmentService.getAllDepartment());
        return "applyment/register";
    }

    /**
     * 참가지 신청 RestApi
     * @param applymentParams ParticipantRegisterParam list 형태로 받음
     * @return
     */
    @PostMapping("register")
    @ResponseBody
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<ApplymentParam>> registerParticipant(@RequestBody List<ApplymentParam> applymentParams) {
        return applymentService.saveApplyment(applymentParams);
    }

    /**
     * 신청 정보 리스트 요청 API
     * 교회명과 종목명 받음
     * @return ApplymentParam - List 형태로 반환
     */
    @GetMapping("applyment")
    @ResponseBody
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<ApplymentParam>> applymentList(@RequestParam("churchName") String churchName, @RequestParam("eventName") String eventName) {
        return applymentService.findApplymentList(churchName, eventName);
    }

    /**
     * 신청 정보 삭제 API
     * @param deleteParam eventName, participantId
     * @return
     */
    @DeleteMapping("applyment")
    @ResponseBody
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity deleteApplyment(@RequestBody DeleteParam deleteParam) {
        return applymentService.deleteApplyment(deleteParam.getEventName(), deleteParam.getParticipantId());
    }

    /**
     * 신청 정보 수정 API
     * @param applymentParam
     * @return
     */
    @PutMapping("applyment")
    @ResponseBody
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity modifyApplyment(@RequestBody ApplymentParam applymentParam) {
        return applymentService.updateApplyment(applymentParam);
    }

    @GetMapping("department")
    @ResponseBody
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<String>> getDepartments() {
        return new ResponseEntity<>(departmentService.getAllDepartment(), HttpStatus.OK);
    }
}
