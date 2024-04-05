package com.pyunggang.churchproject.controller;

import com.pyunggang.churchproject.service.AdminService;
import com.pyunggang.churchproject.service.ChurchService;
import com.pyunggang.churchproject.service.DepartmentService;
import com.pyunggang.churchproject.service.EventService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/*")
public class AdminController {
    final private AdminService adminService;
    final private ChurchService churchService;
    final private EventService eventService;
    final private DepartmentService departmentService;

    // 관리자 로그인 페이지 접근
    @GetMapping("/login")
    public String adminLogin() {
        return "/admin/admin-login";
    }

    // 관리자 페이지 접근
    @GetMapping("/")
    public String adminPage(Model model) {
        model.addAttribute(adminService.getAdminPageInfo());

        return "/admin/admin";
    }

    // 교회 비밀번호 확인 API
    @GetMapping("/church-password")
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> getChurchPassword(@RequestParam("churchName") String churchName) {
        return churchService.findChurchPassword(churchName);
    }

    // 새로운 교회 추가 API
    @PostMapping("/church")
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity addNewChurch(@RequestParam("church") String churchName) {
        return churchService.saveChurch(churchName);
    }

    // 교회 삭제 API
    @DeleteMapping("/church")
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity deleteChurch(@RequestParam("church") String churchName) {
        return churchService.deleteChurch(churchName);
    }

    // 새로운 종목 추가 API
    @PostMapping("/event")
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity addNewEvent(@RequestParam("event") String eventName) {
        return eventService.saveEvent(eventName);
    }

    // 종목 제거 API
    @DeleteMapping("/event")
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity deleteEvent(@RequestParam("event") String eventName) {
        return eventService.removeEvent(eventName);
    }

    // 새로운 부서 추가 API
    @PostMapping("/department")
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity addNewDepartment(@RequestParam("department") String departmentName) {
        return departmentService.addDepartment(departmentName);
    }

    // 부서 제거 API
    @DeleteMapping("/department")
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity deleteDepartment(@RequestParam("department") String departmentName) {
        return departmentService.deleteDepartment(departmentName);
    }

    // TODO: 엑셀 api 하나로 합치기
    // 엑셀 다운로드 링크 반환 API
    @GetMapping("/excel")
    @ResponseBody
    public String getAllInfoAsExcel() {
        return "/admin/excel-download";
    }

    // 다운로드 API
    @GetMapping("/excel-download")
    @ResponseBody
    public void download(HttpServletResponse response) throws IOException {
        adminService.getAllInfoAsExcel(response);
    }

    // TODO: 로그아웃 구현
}
