package com.pyunggang.churchproject.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetChurchNameParam {
    private String churchName;
    private ServerState serverState;

    public enum ServerState {
        OPEN, CLOSE
    }
}
