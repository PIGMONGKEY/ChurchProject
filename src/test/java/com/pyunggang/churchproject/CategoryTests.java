package com.pyunggang.churchproject;

import com.pyunggang.churchproject.data.entity.Category;
import com.pyunggang.churchproject.data.entity.Event;
import com.pyunggang.churchproject.data.repository.CategoryRepository;
import com.pyunggang.churchproject.data.repository.EventRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class CategoryTests {
    @Autowired
    CategoryRepository categoryRepo;
    @Autowired
    EventRepository eventRepo;

    @Test
    public void insertTest() {
        Category category = Category.builder().name("1,2학년").event(eventRepo.findEventByNameIs("그림그리기")).build();
        categoryRepo.save(category);
    }

    @Test
    public void deleteTest() {
        categoryRepo.deleteById(2);
    }
}
