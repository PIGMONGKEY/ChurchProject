package com.pyunggang.churchproject.data.repository;

import com.pyunggang.churchproject.data.entity.Category;
import com.pyunggang.churchproject.data.repository.column.OnlyCategoryName;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Optional<Category> findByNameAndEventName(String categoryName, String eventName);
    boolean existsByNameAndEventName(String categoryName, String eventName);
    List<OnlyCategoryName> findNameByEventName(String eventName);

    @Transactional
    void deleteByNameAndEventName(String categoryName, String eventName);
    @Transactional
    void deleteAllByEventName(String eventName);
}