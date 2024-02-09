package com.b1system.services;

import com.b1system.exceptions.EventNotFoundException;
import com.b1system.models.dtos.CategoryDTO;
import com.b1system.models.entities.Category;
import com.b1system.models.createDtos.CategoryCreateDTO;
import com.b1system.models.entities.Event;
import com.b1system.repository.CategoryRepository;
import com.b1system.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;

    @Autowired
    public CategoryService(final CategoryRepository categoryRepository, EventRepository eventRepository){
        this.categoryRepository = categoryRepository;
        this.eventRepository = eventRepository;
    }

    public CategoryDTO create(final CategoryCreateDTO categoryCreateDTO){
        final Category category = categoryCreateDTOtoCategory(categoryCreateDTO);
        final Category savedCategory = categoryRepository.save(category);
        return categoryToCategoryDTO(savedCategory);
    }

    public List<CategoryDTO> getAllByEventId(final Integer eventId){
        Event event = eventRepository.findById(eventId).orElseThrow(() ->
                new EventNotFoundException("Evento de id " + eventId + " não encontrado"));
        List<Category> categories = categoryRepository.findByEventId(event);
        return categories.stream()
                .map(category -> categoryToCategoryDTO(category))
                .collect(Collectors.toList());
    }

    private Category categoryCreateDTOtoCategory(CategoryCreateDTO categoryCreateDTO){

        Event event = eventRepository.findById(categoryCreateDTO.getEventId()).orElseThrow(() ->
                new EventNotFoundException("Evento de id " + categoryCreateDTO.getEventId() + " não encontrado"));

        return Category.builder()
                .eventId(event)
                .description(categoryCreateDTO.getDescription())
                .remainingSlots(categoryCreateDTO.getSlots())
                .price(categoryCreateDTO.getPrice())
                .memberPrice(categoryCreateDTO.getMemberPrice())
                .build();
    }

    private CategoryDTO categoryToCategoryDTO(Category category){
        Integer event_id = category.getEventId().getId();

        return CategoryDTO.builder()
                .eventId(event_id)
                .description(category.getDescription())
                .slots(category.getRemainingSlots())
                .price(category.getPrice())
                .memberPrice(category.getMemberPrice())
                .build();
    }

}
