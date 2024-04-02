package com.pyunggang.churchproject.data.dto;

import com.pyunggang.churchproject.data.entity.Church;
import com.pyunggang.churchproject.data.entity.Department;
import com.pyunggang.churchproject.data.entity.Event;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminPageParam {
    private List<Church> churches;
    private List<Department> departments;
    private List<Event> events;
}
