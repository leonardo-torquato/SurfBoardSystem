package com.b1system.repository;

import com.b1system.models.Subscription;
import com.b1system.utils.SubscriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {
    Optional<Subscription> findById (Integer id);

    List<Subscription> findByStatus(SubscriptionStatus status);

    List<Subscription> findByFullName(String fullName);

    List<Subscription> findAllByCreatedAtAfter(Timestamp createdAt);

    List<Subscription> findAllByCreatedAtBeforeAndStatus(Timestamp createdAt, SubscriptionStatus status);
}
