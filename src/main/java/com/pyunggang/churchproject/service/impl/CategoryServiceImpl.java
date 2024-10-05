package com.pyunggang.churchproject.service.impl;

import com.pyunggang.churchproject.data.dto.CategoryParam;
import com.pyunggang.churchproject.data.entity.Category;
import com.pyunggang.churchproject.data.entity.Event;
import com.pyunggang.churchproject.data.repository.CategoryRepository;
import com.pyunggang.churchproject.data.repository.EventRepository;
import com.pyunggang.churchproject.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepo;
    private final EventRepository eventRepo;

    /**
     * 카테고리 추가
     * @param categoryParam 카테코리명과 종목명을 파라미터로 받아 새로운 카테고리를 저장한다.
     * @return 이미 존재한다면 409
     * @return 이벤트가 존재하지 않는 이벤트면 404
     * @return 저장에 실패하면 500
     * @return 저장에 성공하면 200
     */
    @Override
    public ResponseEntity addCategory(CategoryParam categoryParam) {
        Event event;
        Category category;

        // 동일한 카테고리명과 종목명을 가진 데이터가 있다면 409 반환
        if (categoryRepo.existsByNameAndEventName(categoryParam.getCategoryName(), categoryParam.getEventName()))
            return new ResponseEntity(HttpStatus.CONFLICT);

        // 파라미터로 받은 이벤트가 존재하지 않는다면 404 반환
        try {
            event = eventRepo.findById(categoryParam.getEventName()).orElseThrow(() -> new IllegalArgumentException("event dosen't exist"));
            category = Category.builder()
                    .name(categoryParam.getCategoryName())
                    .event(event)
                    .build();

        } catch (IllegalArgumentException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        // 저장에 실패한다면 500 반환 / 성공한다면 200 반환
        if (category.equals(categoryRepo.save(category)))
            return new ResponseEntity(HttpStatus.OK);
        else
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
