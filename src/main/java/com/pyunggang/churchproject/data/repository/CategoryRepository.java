package com.pyunggang.churchproject.data.repository;

import com.pyunggang.churchproject.data.entity.Category;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Optional<Category> findByNameAndEventName(String categoryName, String eventName);
    boolean existsByNameAndEventName(String categoryName, String eventName);

    @Transactional
    void deleteByNameAndEventName(String categoryName, String eventName);
}
