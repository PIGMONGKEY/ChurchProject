package com.pyunggang.churchproject.controller;

import com.pyunggang.churchproject.data.dto.NotificationParam;
import com.pyunggang.churchproject.service.*;
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
    final private CategoryService categoryService;

    // 관리자 로그인 페이지 접근
    @GetMapping("login")
    public String adminLogin() {
        return "admin/admin-login";
    }

    // 관리자 페이지 접근
    @GetMapping("")
    public String adminPage(Model model) {
        model.addAttribute(adminService.getAdminPageInfo());

        return "admin/admin";
    }

    // 교회 비밀번호 확인 API
    @GetMapping("church-password")
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> getChurchPassword(@RequestParam("churchName") String churchName) {
        return churchService.findChurchPassword(churchName);
    }

    // 새로운 교회 추가 API
    @PostMapping("church")
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity addNewChurch(@RequestParam("church") String churchName) {
        return churchService.saveChurch(churchName);
    }

    // 교회 삭제 API
    @DeleteMapping("church")
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity deleteChurch(@RequestParam("church") String churchName) {
        return churchService.deleteChurch(churchName);
    }

    // 새로운 종목 추가 API
    @PostMapping("event")
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity addNewEvent(@RequestParam("event") String eventName) {
        return eventService.saveEvent(eventName);
    }

    // 종목 제거 API
    @DeleteMapping("event")
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity deleteEvent(@RequestParam("event") String eventName) {
        return eventService.removeEvent(eventName);
    }

    // 부문 추가 API
    @PostMapping("category")
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity addNewCategory(@RequestParam("event") String eventName, @RequestParam("category") String categoryName) {
        return categoryService.addCategory(categoryName, eventName);
    }

    // 부문 삭제 API
    @DeleteMapping("category")
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity deleteCategory(@RequestParam("event") String eventName, @RequestParam("category") String categoryName) {
        return categoryService.removeCategory(categoryName, eventName);
    }

    // 새로운 부서 추가 API
    @PostMapping("department")
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity addNewDepartment(@RequestParam("department") String departmentName) {
        return departmentService.addDepartment(departmentName);
    }

    // 부서 제거 API
    @DeleteMapping("department")
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity deleteDepartment(@RequestParam("department") String departmentName) {
        return departmentService.deleteDepartment(departmentName);
    }

    // 엑셀 다운로드 링크 반환 API
    @GetMapping("excel")
    @ResponseBody
    public String getAllInfoAsExcel() {
        return "/admin/excel-download";
    }

    // 다운로드 API
    @GetMapping("excel-download")
    @ResponseBody
    public void download(HttpServletResponse response) throws IOException {
        adminService.getAllInfoAsExcel(response);
    }

    // 공지사항 불러오기
    @GetMapping("notification")
    @ResponseBody
    public ResponseEntity<NotificationParam> getNotification() {
        return adminService.getNotification();
    }

    // 공지사항 저장
    @PostMapping("notification")
    @ResponseBody
    public ResponseEntity addNotification(@RequestBody NotificationParam notificationParam) {
        adminService.setNotification(notificationParam);

        return new ResponseEntity(HttpStatus.OK);
    }

    // 공지사항 삭제
    @DeleteMapping("notification")
    @ResponseBody
    public ResponseEntity deleteNotification() {
        adminService.deleteNotification();

        return new ResponseEntity(HttpStatus.OK);
    }

    // 참가 신청 가능 여부 변경
    @PostMapping("serverState")
    @ResponseBody
    public ResponseEntity changeServerState() {
        return adminService.changeServerState();
    }
}
