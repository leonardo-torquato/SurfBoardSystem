package com.b1system.services;

import com.b1system.exceptions.EventNotFoundException;
import com.b1system.models.entities.Category;
import com.b1system.models.createDtos.CategoryCreateDTO;
import com.b1system.models.entities.Event;
import com.b1system.repository.CategoryRepository;
import com.b1system.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;

    @Autowired
    public CategoryService(final CategoryRepository categoryRepository, EventRepository eventRepository){
        this.categoryRepository = categoryRepository;
        this.eventRepository = eventRepository;
    }

    public CategoryCreateDTO create(final CategoryCreateDTO categoryDTO){
        final Category category = categoryDTOtoCategory(categoryDTO);
        final Category savedCategory = categoryRepository.save(category);
        return categoryToCategoryDTO(savedCategory);
    }

    private Category categoryDTOtoCategory(CategoryCreateDTO categoryDTO){

        Event event = eventRepository.findById(categoryDTO.getEventId()).orElseThrow(() ->
                new EventNotFoundException("Evento de id " + categoryDTO.getEventId() + " não encontrado."));

        return Category.builder()
                .eventId(event)
                .description(categoryDTO.getDescription())
                .remainingSlots(categoryDTO.getSlots())
                .price(categoryDTO.getPrice())
                .memberPrice(categoryDTO.getMemberPrice())
                .build();
    }

    private CategoryCreateDTO categoryToCategoryDTO(Category category){
        Integer event_id = category.getEventId().getId();

        return CategoryCreateDTO.builder()
                .eventId(event_id)
                .description(category.getDescription())
                .slots(category.getRemainingSlots())
                .price(category.getPrice())
                .memberPrice(category.getMemberPrice())
                .build();
    }

}
