package com.pyunggang.churchproject;

import com.pyunggang.churchproject.data.dto.CategoryParam;
import com.pyunggang.churchproject.data.entity.Category;
import com.pyunggang.churchproject.data.entity.Event;
import com.pyunggang.churchproject.data.repository.CategoryRepository;
import com.pyunggang.churchproject.data.repository.EventRepository;
import com.pyunggang.churchproject.service.CategoryService;
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
    @Autowired
    CategoryService categoryService;

    @Test
    public void insertTest() {
//        Category category = Category.builder().name("1,2학년").event(eventRepo.findEventByNameIs("그림그리기")).build();
//        categoryRepo.save(category);

//        // 성공, 중복 테스트
//        System.out.println(categoryService.addCategory(new CategoryParam("3,4학년", "그림그리기")).getStatusCode());

//        // 이벤트 없음 테스트
//        System.out.println(categoryService.addCategory(new CategoryParam("3,4학년", "없는종목")).getStatusCode());
    }

    @Test
    public void deleteTest() {
//        categoryRepo.deleteById(2);
//        categoryRepo.deleteByNameAndEventName("1,2학년", "그림그리기");

//        // 존재하지 않음 테스트
//        System.out.println(categoryService.removeCategory(new CategoryParam("1,2학년", "그림그리기")));

//        // 성공 테스트
//        System.out.println(categoryService.removeCategory(new CategoryParam("3,4학년", "그림그리기")).getStatusCode());

//        // 없는 이벤트 테스트
//        System.out.println(categoryService.removeCategory(new CategoryParam("3,4학년", "없는 종목")).getStatusCode());

        categoryRepo.deleteAllByEventName("그림그리기");
    }

    @Test
    public void existsTest() {
//        System.out.println(categoryRepo.existsByNameAndEventName("1, 2학년", "글짓기"));
    }

    @Test
    public void findTest() {
        categoryRepo.findNameByEventName("그림그리기").forEach(onlyCategoryName -> {System.out.println(onlyCategoryName.getName());});
    }
}
