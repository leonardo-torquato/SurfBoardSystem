package com.b1system.services;

import com.b1system.models.dtos.CategoryDTO;
import com.b1system.models.dtos.EventDTO;
import com.b1system.models.entities.Event;
import com.b1system.models.createDtos.EventCreateDTO;
import com.b1system.repository.EventRepository;
import com.b1system.utils.EventStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {

    private final EventRepository eventRepository;

    //TODO exceptions

    @Autowired
    public EventService(final EventRepository eventRepository){
        this.eventRepository = eventRepository;
    }

    public EventDTO create(final EventCreateDTO eventDTO){
        final Event event = eventDTOToEvent(eventDTO);
        event.setStatus(EventStatus.OPEN);
        final Event savedEvent = eventRepository.save(event);
        return eventToEventDTO(savedEvent);
    }

    public List<EventDTO> getAll(){
        List<Event> events =  eventRepository.findAll();
        return events.stream()
                .map(event -> eventToEventDTO(event))
                .collect(Collectors.toList());
    }

    private Event eventDTOToEvent(EventCreateDTO eventDTO){
        return Event.builder()
                .name(eventDTO.getName())
                .description(eventDTO.getDescription())
                .build();
    }

    private EventDTO eventToEventDTO(Event event){
        return EventDTO.builder()
                .name(event.getName())
                .description(event.getDescription())
                .build();
    }

}
