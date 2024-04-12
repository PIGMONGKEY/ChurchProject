package com.pyunggang.churchproject.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
    // 토큰에서 교회명 추출하여 리턴
    public static String getCurrentChurchName() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getName() == null) {
            throw new RuntimeException("No authentication information");
        }

        return authentication.getName();
    }
}
