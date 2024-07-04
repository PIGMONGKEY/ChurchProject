package com.pyunggang.churchproject.data.dto;

import com.pyunggang.churchproject.utils.ServerState;
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
}
