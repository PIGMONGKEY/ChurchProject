package com.pyunggang.churchproject.service;

import com.pyunggang.churchproject.data.dto.ApplymentParam;

import java.util.List;

public interface ApplymentService {
    public boolean saveApplyment(List<ApplymentParam> params);
    public List<ApplymentParam> findApplymentList(String churchName, String eventName);
    public boolean deleteApplyment(String eventName, int participantId);
    public boolean updateApplyment(ApplymentParam applymentParam);
}
