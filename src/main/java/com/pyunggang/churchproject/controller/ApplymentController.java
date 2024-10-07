package com.pyunggang.churchproject.controller;

import com.pyunggang.churchproject.data.dto.DeleteApplymentParam;
import com.pyunggang.churchproject.data.dto.ApplymentParam;
import com.pyunggang.churchproject.data.dto.GetChurchNameParam;
import com.pyunggang.churchproject.data.repository.CategoryRepository;
import com.pyunggang.churchproject.data.repository.column.OnlyCategoryName;
import com.pyunggang.churchproject.service.*;
import com.pyunggang.churchproject.utils.SecurityUtil;
import com.pyunggang.churchproject.utils.ServerState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
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
    final CategoryRepository categoryRepo;
    final CategoryService categoryService;
    final RedisTemplate<String, Object> redisTemplate;

    // 교회 홈
    @GetMapping("home")
    public String registerHome(Model model) {
        model.addAttribute("events", eventService.findAllEventNames().getBody());
        model.addAttribute("departments", departmentService.getAllDepartment());
        return "applyment/home";
    }

    // 로그인한 교회 이름 API
    @GetMapping("church")
    @ResponseBody
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<GetChurchNameParam> getChurchName() {
        GetChurchNameParam churchNameParam = GetChurchNameParam.builder()
                .churchName(SecurityUtil.getCurrentChurchName())
                .serverState(redisTemplate.opsForValue().get("server_state").toString().equals("OPEN") ? ServerState.OPEN : ServerState.CLOSE)
                .build();

        return new ResponseEntity<>(churchNameParam, HttpStatus.OK);
    }

    // 종목에 대한 부문 조회 API
    @GetMapping("category")
    @ResponseBody
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<OnlyCategoryName>> getCategories(@RequestParam("eventName") String eventName) {
        return categoryService.findAllCategoryByEvent(eventName);
    }

    /**
     * 참가자 신청 페이지로 이동
     * 교회명, 종목명, 부서명, 부문명을 넘겨줌
     * @param churchName 교회 이름
     * @param eventName 종목 이름
     */
    @GetMapping("register")
    public String registerParticipant(@RequestParam("churchName") String churchName, @RequestParam("eventName") String eventName, Model model) {
        model.addAttribute("church", churchName);
        model.addAttribute("event", eventName);
        model.addAttribute("departments", departmentService.getAllDepartment());
        // TODO: Category 넘겨주기
        model.addAttribute("categories", categoryRepo.findNameByEventName(eventName));
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
     * @param deleteApplymentParam eventName, participantId
     * @return
     */
    @DeleteMapping("applyment")
    @ResponseBody
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity deleteApplyment(@RequestBody DeleteApplymentParam deleteApplymentParam) {
        return applymentService.deleteApplyment(deleteApplymentParam.getEventName(), deleteApplymentParam.getParticipantId());
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

    /**
     * 종목 정보 불러오기 API
     * @return List<String> 형태로 종목 리스트 반환
     */
    @GetMapping("department")
    @ResponseBody
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<String>> getDepartments() {
        return new ResponseEntity<>(departmentService.getAllDepartment(), HttpStatus.OK);
    }
}
