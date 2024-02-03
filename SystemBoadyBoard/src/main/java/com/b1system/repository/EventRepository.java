package com.b1system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<EventRepository, Integer> {
    Optional<EventRepository> findById (Integer id);
}
