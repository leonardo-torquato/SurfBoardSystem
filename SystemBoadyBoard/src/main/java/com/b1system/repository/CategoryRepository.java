package com.b1system.repository;

import com.b1system.models.entities.Category;
import com.b1system.models.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Optional<Category> findById (Integer id);

    List<Category> findByEventId(Event event);
    //Optional<Set<Category>> findAllById (Set<Integer> ids);

    //public Optional<List<Category>> getCategoriesBy
}
