package com.pyunggang.churchproject.service;

import com.pyunggang.churchproject.data.repository.column.OnlyCategoryName;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CategoryService {
    ResponseEntity addCategory(String categoryName, String eventName);
    ResponseEntity removeCategory(String categoryName, String eventName);
    ResponseEntity<List<OnlyCategoryName>> findAllCategoryByEvent(String eventName);
}
