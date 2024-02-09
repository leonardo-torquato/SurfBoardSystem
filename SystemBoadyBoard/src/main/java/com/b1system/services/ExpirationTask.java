package com.b1system.services;

import com.b1system.models.entities.Category;
import com.b1system.repository.CategoryRepository;
import com.b1system.repository.SubscriptionRepository;
import com.b1system.utils.SubscriptionStatus;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

@Service
@EnableScheduling
public class ExpirationTask {

    private final SubscriptionRepository subscriptionRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ExpirationTask(SubscriptionRepository subscriptionRepository, CategoryRepository categoryRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.categoryRepository = categoryRepository;
    }

    // a cada 5 minutos olha por inscrições que estão pendendo a mais de 30 minutos. Se houverem, altera para expiradas
    @Transactional
    @Scheduled(fixedRate = 5 * 60 * 1000) // a cada 5 minutos
    public void expireSubscriptions(){
        long thirtyMinutesAgo = System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(30);
        subscriptionRepository.findAllByCreatedAtBeforeAndStatus(new Timestamp(thirtyMinutesAgo), SubscriptionStatus.PENDING)
                .forEach(subscription -> {
                    subscription.setStatus(SubscriptionStatus.EXPIRED);

                    for (Category category : subscription.getCategories()){
                        category.openSlot();
                        categoryRepository.save(category);
                    }

                    subscriptionRepository.save(subscription);
                });
    }

}
