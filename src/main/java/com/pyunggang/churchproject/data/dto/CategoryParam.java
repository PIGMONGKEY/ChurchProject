package com.pyunggang.churchproject.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryParam {
    private String categoryName;
    private String eventName;
}
