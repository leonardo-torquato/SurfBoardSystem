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
import com.b1system.utils.SubscriptionStatus;
import com.b1system.utils.UnidadeFederacao;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Transactional
    public SubscriptionDTO create(SubscriptionDTO subscriptionDTO){
        final Subscription subscription = subscriptionDTOtoSubscription(subscriptionDTO);

        subscription.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        subscription.setStatus(SubscriptionStatus.PENDING); //inicializa como pagamento pendendo

        for (Category category : subscription.getCategories()){
            category.deductSlot();
        }

        final Subscription savedSubscription = subscriptionRepository.save(subscription);
        return subscriptionToSubscriptionDTO(savedSubscription);
    }

    @Transactional
    public SubscriptionDTO paid(Subscription subscription){
        subscription.setStatus(SubscriptionStatus.PAID);
        Subscription updatedSubscription = subscriptionRepository.save(subscription);
        return subscriptionToSubscriptionDTO(updatedSubscription);
    }

    // get methods

    public List<SubscriptionDTO> getALlSubscriptionsDTO(){
        List<Subscription> subscriptions = subscriptionRepository.findAll();
        return subscriptions.stream()
                .map(this::subscriptionToSubscriptionDTO)
                .collect(Collectors.toList());
    }

    public List<SubscriptionDTO> getPaidSubscriptionsDTO(){
        List<Subscription> subscriptions = subscriptionRepository.findByStatus(SubscriptionStatus.PAID);
        return subscriptions.stream()
                .map(this::subscriptionToSubscriptionDTO)
                .collect(Collectors.toList());
    }

    public List<SubscriptionDTO> getUserSubscriptionsDTO(String fullName){
        List<Subscription> subscriptions = subscriptionRepository.findByFullName(fullName);
        return subscriptions.stream()
                .map(this::subscriptionToSubscriptionDTO)
                .collect(Collectors.toList());
    }

    // ...



    private Subscription subscriptionDTOtoSubscription(SubscriptionDTO subscriptionDTO){
        Event eventId = eventRepository.findById(subscriptionDTO.getEventId()).orElseThrow(() ->
                new EventNotFoundException("Evento de id " + subscriptionDTO.getEventId() + " não encontrado."));

        List<Category> categoriesIds = categoryRepository.findAllById(subscriptionDTO.getCategoriesId());//.orElseThrow(() ->
                //new CategoryNotFoundException("Categorias de id " + subscriptionDTO.getCategoriesId() + " não encontradas."));

        //TODO: verificar se usuário tem permissão de se inscrever nessas categoria

        //calcula preço total de todas as categorias inscritas
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (Category category : categoriesIds){
            totalPrice = totalPrice.add(category.getPrice());
        }

        UnidadeFederacao federacao = UnidadeFederacao.fromSigla(subscriptionDTO.getSiglaFederacao());

        return Subscription.builder()
                .eventId(eventId)
                .categories(categoriesIds)
                .cpf(subscriptionDTO.getCpf())
                .email(subscriptionDTO.getEmail())
                .fullName(subscriptionDTO.getFullName())
                .nickname(subscriptionDTO.getNickname())
                .birthDate(subscriptionDTO.getBirthDate())
                .federation(federacao)
                .picture(subscriptionDTO.getPicture())
                .totalPrice(totalPrice)
                .build();
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
                .siglaFederacao(siglaFederacao)
                .picture(subscription.getPicture())
                .build();
    }

}
