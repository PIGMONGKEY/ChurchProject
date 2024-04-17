package com.pyunggang.churchproject.service.impl;

import com.pyunggang.churchproject.data.dto.AdminPageParam;
import com.pyunggang.churchproject.data.entity.*;
import com.pyunggang.churchproject.data.repository.*;
import com.pyunggang.churchproject.service.AdminService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Time;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminServiceImpl implements AdminService {
    final private ChurchRepository churchRepository;
    final private EventRepository eventRepository;
    final private DepartmentRepository departmentRepository;
    final private ApplymentRepository applymentRepository;
    final private ParticipantRepository participantRepository;

    // 관리자 페이지 정보 리턴
    // 교회명, 부서명, 종목명 검색하여 리턴
    @Override
    public AdminPageParam getAdminPageInfo() {
        AdminPageParam adminPageParam = AdminPageParam.builder()
                .churches(churchRepository.findAll())
                .departments(departmentRepository.findAll())
                .events(eventRepository.findAll())
                .build();

        return adminPageParam;
    }

    // DB 속 모든 정보를 excel로 다운로드
    @Override
    public void getAllInfoAsExcel(HttpServletResponse response) throws IOException {
        final String[] cellNames = {"종목", "교회", "이름", "나이", "학년", "부서", "성별"};

        List<Event> events = eventRepository.findAll();
        List<Church> churches = churchRepository.findAll();
        List<Department> departments = departmentRepository.findAll();
        List<Applyment> applyments;
        List<Participant> participants;
        Workbook wb = new XSSFWorkbook();
        Row row = null;
        Cell cell = null;
        int rowNum = 0;
        Sheet sheet;

        // 전체 Sheet
        participants = participantRepository.findAll();

        rowNum = 0;
        // 전체 챀가자 Sheet 생성
        sheet = wb.createSheet("전체 참가자");
        row = sheet.createRow(rowNum++);
        for (int i=0; i<cellNames.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(cellNames[i]);
        }

        // 데이터 삽입
        for (Participant participant : participants) {
            row = sheet.createRow(rowNum++);
            String eventNames = "";
            // 참여하는 모든 종목 문자열로 연결
            for (Applyment applyment : participant.getEvents())
                eventNames += ", " + applyment.getEvent().getName();
            eventNames = eventNames.substring(2);
            // 종목명
            cell = row.createCell(0);
            cell.setCellValue(eventNames);
            // 교회명
            cell = row.createCell(1);
            cell.setCellValue(participant.getChurch().getName());
            // 이름
            cell = row.createCell(2);
            cell.setCellValue(participant.getName());
            // 나이
            cell = row.createCell(3);
            cell.setCellValue(participant.getAge());
            // 학년
            cell = row.createCell(4);
            cell.setCellValue(participant.getGrade() + "학년");
            // 부서
            cell = row.createCell(5);
            cell.setCellValue(participant.getDepartment().getName());
            // 성별
            cell = row.createCell(6);
            cell.setCellValue(participant.getGender());
        }

        // 종목 Sheet
        for (Event event : events) {
            rowNum = 0;
            applyments = applymentRepository.findAllByEventName(event.getName());
            // Sheet 생성
            sheet = wb.createSheet(event.getName());
            // 제목 줄 생성
            row = sheet.createRow(rowNum++);
            // 제목 값 입력 - 종목 제외
            for (int i=0; i<cellNames.length; i++) {
                cell = row.createCell(i);
                cell.setCellValue(cellNames[i]);
            }

            // 데이터 삽입
            for (Applyment applyment : applyments) {
                row = sheet.createRow(rowNum++);
                // 종목명
                cell = row.createCell(0);
                cell.setCellValue(applyment.getEvent().getName());
                // 교회명
                cell = row.createCell(1);
                cell.setCellValue(applyment.getParticipant().getChurch().getName());
                // 이름
                cell = row.createCell(2);
                cell.setCellValue(applyment.getParticipant().getName());
                // 나이
                cell = row.createCell(3);
                cell.setCellValue(applyment.getParticipant().getAge());
                // 학년
                cell = row.createCell(4);
                cell.setCellValue(applyment.getParticipant().getGrade() + "학년");
                // 부서
                cell = row.createCell(5);
                cell.setCellValue(applyment.getParticipant().getDepartment().getName());
                // 성별
                cell = row.createCell(6);
                cell.setCellValue(applyment.getParticipant().getGender());
            }
        }

        // 부서 Sheet
        for (Department department : departments) {
            participants = participantRepository.findAllByDepartmentName(department.getName());

            rowNum = 0;
            // 부서명 Sheet 생성
            sheet = wb.createSheet(department.getName());
            row = sheet.createRow(rowNum++);
            for (int i=0; i<cellNames.length; i++) {
                cell = row.createCell(i);
                cell.setCellValue(cellNames[i]);
            }

            // 데이터 삽입
            for (Participant participant : participants) {
                row = sheet.createRow(rowNum++);
                String eventNames = "";
                // 참여하는 모든 종목 문자열로 연결
                for (Applyment applyment : participant.getEvents())
                    eventNames += ", " + applyment.getEvent().getName();
                eventNames = eventNames.substring(2);
                // 종목명
                cell = row.createCell(0);
                cell.setCellValue(eventNames);
                // 교회명
                cell = row.createCell(1);
                cell.setCellValue(participant.getChurch().getName());
                // 이름
                cell = row.createCell(2);
                cell.setCellValue(participant.getName());
                // 나이
                cell = row.createCell(3);
                cell.setCellValue(participant.getAge());
                // 학년
                cell = row.createCell(4);
                cell.setCellValue(participant.getGrade() + "학년");
                // 부서
                cell = row.createCell(5);
                cell.setCellValue(participant.getDepartment().getName());
                // 성별
                cell = row.createCell(6);
                cell.setCellValue(participant.getGender());
            }
        }

        // 교회 Sheet
        for (Church church : churches) {
            participants = participantRepository.findAllByChurchName(church.getName());

            rowNum = 0;
            // 교회명 Sheet 생성
            sheet = wb.createSheet(church.getName());
            row = sheet.createRow(rowNum++);
            for (int i=0; i<cellNames.length; i++) {
                cell = row.createCell(i);
                cell.setCellValue(cellNames[i]);
            }

            // 데이터 삽입
            for (Participant participant : participants) {
                row = sheet.createRow(rowNum++);
                String eventNames = "";
                // 참여하는 모든 종목 문자열로 연결
                for (Applyment applyment : participant.getEvents())
                    eventNames += ", " + applyment.getEvent().getName();
                eventNames = eventNames.substring(2);
                // 종목명
                cell = row.createCell(0);
                cell.setCellValue(eventNames);
                // 교회명
                cell = row.createCell(1);
                cell.setCellValue(participant.getChurch().getName());
                // 이름
                cell = row.createCell(2);
                cell.setCellValue(participant.getName());
                // 나이
                cell = row.createCell(3);
                cell.setCellValue(participant.getAge());
                // 학년
                cell = row.createCell(4);
                cell.setCellValue(participant.getGrade() + "학년");
                // 부서
                cell = row.createCell(5);
                cell.setCellValue(participant.getDepartment().getName());
                // 성별
                cell = row.createCell(6);
                cell.setCellValue(participant.getGender());
            }
        }

        // 컨텐츠 타입과 파일명 지정
        response.setContentType("ms-vnd/excel");
        response.setHeader("Content-Disposition", "attachment;filename=노회대회_참가자_신청_현황.xlsx");

        wb.write(response.getOutputStream());
        wb.close();
    }
}
