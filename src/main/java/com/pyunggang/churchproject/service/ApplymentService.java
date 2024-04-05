package com.pyunggang.churchproject.service;

import com.pyunggang.churchproject.data.dto.ApplymentParam;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ApplymentService {
    public ResponseEntity saveApplyment(List<ApplymentParam> params);
    public ResponseEntity<List<ApplymentParam>> findApplymentList(String churchName, String eventName);
    public ResponseEntity deleteApplyment(String eventName, int participantId);
    public ResponseEntity updateApplyment(ApplymentParam applymentParam);
}
