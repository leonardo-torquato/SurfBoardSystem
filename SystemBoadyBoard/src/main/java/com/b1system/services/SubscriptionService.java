package com.b1system.services;

import com.b1system.exceptions.CategoryNotFoundException;
import com.b1system.exceptions.EventNotFoundException;
import com.b1system.models.Category;
import com.b1system.models.Event;
import com.b1system.models.Subscription;
import com.b1system.models.SubscriptionDTO;
import com.b1system.repository.CategoryRepository;
import com.b1system.repository.EventRepository;
import com.b1system.repository.SubscriptionRepository;
import com.b1system.utils.UnidadeFederacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public SubscriptionService(final SubscriptionRepository subscriptionRepository,
                               final EventRepository eventRepository,
                               final CategoryRepository categoryRepository){
        this.subscriptionRepository = subscriptionRepository;
        this.eventRepository = eventRepository;
        this.categoryRepository = categoryRepository;
    }

    public SubscriptionDTO create(SubscriptionDTO subscriptionDTO){
        final Subscription subscription = subscriptionDTOtoSubscription(subscriptionDTO);
        final Subscription savedSubscription = subscriptionRepository.save(subscription);
        return subscriptionToSubscriptionDTO(savedSubscription);
    }

    private Subscription subscriptionDTOtoSubscription(SubscriptionDTO subscriptionDTO){
        Event eventId = eventRepository.findById(subscriptionDTO.getEventId()).orElseThrow(() ->
                new EventNotFoundException("Evento de id " + subscriptionDTO.getEventId() + " não encontrado."));

        Set<Category> categoriesIds = categoryRepository.findAllById(subscriptionDTO.getCategoriesId()).orElseThrow(() ->
                new CategoryNotFoundException("Categorias de id " + subscriptionDTO.getCategoriesId() + " não encontradas."));

        UnidadeFederacao federacao = UnidadeFederacao.fromSigla(subscriptionDTO.getSiglaFederacao());

        return Subscription.builder()
                .eventId(eventId)
                .categories(categoriesIds)
                .cpf(subscriptionDTO.getCpf())
                .email(subscriptionDTO.getEmail())
                .fullName(subscriptionDTO.getFullName())
                .nickname(subscriptionDTO.getNickname())
                .birthDate(subscriptionDTO.getBirthDate())
                .federation(federacao).build();
    }

    private SubscriptionDTO subscriptionToSubscriptionDTO(Subscription subscription){
        Integer eventId = subscription.getEventId().getId();

        Set<Integer> categoriesIds = new HashSet<>();
        for (Category category : subscription.getCategories()) {
            categoriesIds.add(category.getId());
        }

        String siglaFederacao = subscription.getFederation().sigla();

        return SubscriptionDTO.builder()
                .eventId(eventId)
                .categoriesId(categoriesIds)
                .cpf(subscription.getCpf())
                .email(subscription.getEmail())
                .fullName(subscription.getFullName())
                .nickname(subscription.getNickname())
                .birthDate(subscription.getBirthDate())
                .siglaFederacao(siglaFederacao).build();
    }

}
