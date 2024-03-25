package com.pyunggang.churchproject.data.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HomeParam {
    private String churchName;
    private String password;
    private String eventName;

    @Builder
    public HomeParam(String churchName, String password, String eventName) {
        this.churchName = churchName;
        this.password = password;
        this.eventName = eventName;
    }
}
