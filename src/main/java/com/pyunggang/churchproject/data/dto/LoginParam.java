package com.pyunggang.churchproject.data.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginParam {
    private String churchName;
    private String password;
    private String eventName;

    @Builder
    public LoginParam(String churchName, String password, String eventName) {
        this.churchName = churchName;
        this.password = password;
        this.eventName = eventName;
    }
}
