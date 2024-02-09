package com.b1system.services;

import com.b1system.exceptions.EventNotFoundException;
import com.b1system.exceptions.UserNotFoundException;
import com.b1system.models.createDtos.SubscriptionCreateDTO;
import com.b1system.models.entities.User;
import com.b1system.models.entities.Category;
import com.b1system.models.entities.Event;
import com.b1system.models.entities.Subscription;
import com.b1system.repository.CategoryRepository;
import com.b1system.repository.EventRepository;
import com.b1system.repository.SubscriptionRepository;
import com.b1system.repository.UserRepository;
import com.b1system.utils.SubscriptionStatus;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final UserRepository userRepository;

    @Autowired
    public SubscriptionService(final SubscriptionRepository subscriptionRepository,
                               final EventRepository eventRepository,
                               final CategoryRepository categoryRepository,
                               final UserRepository userRepository){
        this.subscriptionRepository = subscriptionRepository;
        this.eventRepository = eventRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public SubscriptionCreateDTO create(SubscriptionCreateDTO subscriptionDTO){
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
    public SubscriptionCreateDTO paid(Subscription subscription){
        subscription.setStatus(SubscriptionStatus.PAID);
        Subscription updatedSubscription = subscriptionRepository.save(subscription);
        return subscriptionToSubscriptionDTO(updatedSubscription);
    }

    // get methods

    public List<SubscriptionCreateDTO> getALlSubscriptionsDTO(){
        List<Subscription> subscriptions = subscriptionRepository.findAll();
        return subscriptions.stream()
                .map(this::subscriptionToSubscriptionDTO)
                .collect(Collectors.toList());
    }

    public List<SubscriptionCreateDTO> getPaidSubscriptionsDTO(){
        List<Subscription> subscriptions = subscriptionRepository.findByStatus(SubscriptionStatus.PAID);
        return subscriptions.stream()
                .map(this::subscriptionToSubscriptionDTO)
                .collect(Collectors.toList());
    }

    public List<SubscriptionCreateDTO> getUserSubscriptionsDTO(String fullName){
        List<Subscription> subscriptions = subscriptionRepository.findByFullName(fullName);
        return subscriptions.stream()
                .map(this::subscriptionToSubscriptionDTO)
                .collect(Collectors.toList());
    }

    // ...



    private Subscription subscriptionDTOtoSubscription(SubscriptionCreateDTO subscriptionDTO){
        Event eventId = eventRepository.findById(subscriptionDTO.getEventId()).orElseThrow(() ->
                new EventNotFoundException("Evento de id " + subscriptionDTO.getEventId() + " não encontrado."));

        List<Category> categoriesIds = categoryRepository.findAllById(subscriptionDTO.getCategoriesId());//.orElseThrow(() ->
                //new CategoryNotFoundException("Categorias de id " + subscriptionDTO.getCategoriesId() + " não encontradas."));

        User user =  userRepository.findById(subscriptionDTO.getUserId()).orElseThrow(() ->
                new UserNotFoundException("Usuário de id " + subscriptionDTO.getUserId() + " não encontrado."));

        //TODO: verificar se usuário é o mesmo que esta fazendo o cadastro
        //TODO: verificar se usuário tem permissão de se inscrever nessas categoria

        //calcula preço total de todas as categorias inscritas
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (Category category : categoriesIds){
            totalPrice = totalPrice.add(category.getPrice());
        }

        //UnidadeFederacao federacao = UnidadeFederacao.fromSigla(user.getSiglaFederacao());

        return Subscription.builder()
                .eventId(eventId)
                .categories(categoriesIds)
                .user(user)
                .totalPrice(totalPrice)
                .build();
    }

    private SubscriptionCreateDTO subscriptionToSubscriptionDTO(Subscription subscription){
        Integer eventId = subscription.getEventId().getId();

        Set<Integer> categoriesIds = new HashSet<>();
        for (Category category : subscription.getCategories()) {
            categoriesIds.add(category.getId());
        }

        Integer userId = subscription.getUser().getUserId();

        return SubscriptionCreateDTO.builder()
                .eventId(eventId)
                .categoriesId(categoriesIds)
                .userId(userId)
                .build();
    }

}
