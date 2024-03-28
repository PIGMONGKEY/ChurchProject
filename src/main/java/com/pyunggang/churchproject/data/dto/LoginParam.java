package com.pyunggang.churchproject.data.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

@Data
@NoArgsConstructor
public class LoginParam {
    private String churchName;
    private String password;

    @Builder
    public LoginParam(String churchName, String password) {
        this.churchName = churchName;
        this.password = password;
    }
}
