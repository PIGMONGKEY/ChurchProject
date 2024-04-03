package com.pyunggang.churchproject.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TokenInfoParam {
    // 인증 타입 : Bearer 사용할 예정
    private String grantType;
    private String accessToken;
    private String refreshToken;
}
