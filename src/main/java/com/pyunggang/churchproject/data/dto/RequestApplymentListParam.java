package com.pyunggang.churchproject.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestApplymentListParam {
    private String eventName;
    private String churchName;
}
