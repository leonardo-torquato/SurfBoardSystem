package com.b1system.repository;

import com.b1system.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Optional<Category> findById (Integer id);
    Optional<Set<Category>> findAllById (Set<Integer> ids);
}
