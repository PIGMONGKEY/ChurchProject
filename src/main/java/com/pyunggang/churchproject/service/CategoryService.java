package com.pyunggang.churchproject.service;

import com.pyunggang.churchproject.data.dto.CategoryParam;
import com.pyunggang.churchproject.data.repository.column.OnlyCategoryName;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CategoryService {
    ResponseEntity addCategory(CategoryParam categoryParam);
    ResponseEntity removeCategory(CategoryParam categoryParam);
    ResponseEntity<List<OnlyCategoryName>> findAllCategoryByEvent(String eventName);
}
