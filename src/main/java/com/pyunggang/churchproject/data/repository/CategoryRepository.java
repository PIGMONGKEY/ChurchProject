package com.pyunggang.churchproject.data.repository;

import com.pyunggang.churchproject.data.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    boolean existsByNameAndEventName(String categoryName, String eventName);
}
