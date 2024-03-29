package com.pyunggang.churchproject.service;

import com.pyunggang.churchproject.data.dto.ParticipantRegisterParam;
import com.pyunggang.churchproject.data.entity.Applyment;

import java.util.List;

public interface ApplymentService {
    public boolean saveApplyment(List<ParticipantRegisterParam> params);
    public List<ParticipantRegisterParam> findApplymentList(String churchName, String eventName);
}
