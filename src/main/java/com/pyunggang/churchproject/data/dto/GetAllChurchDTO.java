package com.pyunggang.churchproject.data.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class GetAllChurchDTO {
    private String church;

    @Builder
    public GetAllChurchDTO(String church) {
        this.church = church;
    }
}
