package com.pyunggang.churchproject.service.impl;

import com.pyunggang.churchproject.data.entity.Applyment;
import com.pyunggang.churchproject.data.entity.Category;
import com.pyunggang.churchproject.data.entity.Event;
import com.pyunggang.churchproject.data.entity.Participant;
import com.pyunggang.churchproject.data.repository.ApplymentRepository;
import com.pyunggang.churchproject.data.repository.CategoryRepository;
import com.pyunggang.churchproject.data.repository.EventRepository;
import com.pyunggang.churchproject.data.repository.ParticipantRepository;
import com.pyunggang.churchproject.data.repository.column.OnlyCategoryName;
import com.pyunggang.churchproject.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepo;
    private final EventRepository eventRepo;
    private final ApplymentRepository applymentRepo;
    private final ParticipantRepository participantRepo;

    /**
     * 카테고리 추가
     * @param categoryName 카테코리명과 종목명을 파라미터로 받아 새로운 카테고리를 저장한다.
     * @param eventName
     * @return 이미 존재한다면 409
     * @return 이벤트가 존재하지 않는 이벤트면 404
     * @return 저장에 실패하면 500
     * @return 저장에 성공하면 200
     */
    @Override
    public ResponseEntity addCategory(String categoryName, String eventName) {
        Event event;
        Category category;

        // 동일한 카테고리명과 종목명을 가진 데이터가 있다면 409 반환
        if (categoryRepo.existsByNameAndEventName(categoryName, eventName))
            return new ResponseEntity(HttpStatus.CONFLICT);

        // 파라미터로 받은 이벤트가 존재하지 않는다면 404 반환
        try {
            event = eventRepo.findById(eventName).orElseThrow(() -> new IllegalArgumentException("event dosen't exist"));
            category = Category.builder()
                    .name(categoryName)
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

    /**
     * 카테고리 삭제 메서드
     * @param categoryName
     * @param eventName
     * 카테고리가 존재하는지 확인한 후 삭제
     * @return 삭제 성공시 200
     * @return 존재하지 않으면 404
     */
    @Override
    public ResponseEntity removeCategory(String categoryName, String eventName) {
        // TODO: 부문 삭제 시 해당 참여 정보 삭제 로직 추가
        List<Applyment> applyments;

        if (!categoryRepo.existsByNameAndEventName(categoryName, eventName))
            return new ResponseEntity(HttpStatus.NOT_FOUND);

        applyments = applymentRepo.findAllByCategoryNameAndEventName(categoryName, eventName);

        if (!applyments.isEmpty()) {
            applymentRepo.deleteAll(applyments);
            for (Applyment applyment : applyments) {
                if (!applymentRepo.existsByParticipant(applyment.getParticipant()))
                    participantRepo.delete(applyment.getParticipant());
            }
        }

        categoryRepo.deleteByNameAndEventName(categoryName, eventName);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 이벤트명으로 해당 카테고리 모두 조회 메서드
     * @param eventName
     * @return 200 + 카테고리 리스트
     */
    @Override
    public ResponseEntity<List<OnlyCategoryName>> findAllCategoryByEvent(String eventName) {
        return new ResponseEntity<>(categoryRepo.findNameByEventName(eventName), HttpStatus.OK);
    }
}
