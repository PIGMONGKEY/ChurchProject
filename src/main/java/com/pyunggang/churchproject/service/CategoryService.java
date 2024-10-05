package com.pyunggang.churchproject.service;

import com.pyunggang.churchproject.data.dto.CategoryParam;
import org.springframework.http.ResponseEntity;

public interface CategoryService {
    ResponseEntity addCategory(CategoryParam categoryParam);
    ResponseEntity removeCategory(CategoryParam categoryParam);
}
