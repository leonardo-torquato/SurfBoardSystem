package com.b1system.services;

import com.b1system.models.entities.Event;
import com.b1system.models.createDtos.EventCreateDTO;
import com.b1system.repository.EventRepository;
import com.b1system.utils.EventStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventService(final EventRepository eventRepository){
        this.eventRepository = eventRepository;
    }

    public EventCreateDTO create(final EventCreateDTO eventDTO){
        final Event event = eventDTOToEvent(eventDTO);
        event.setStatus(EventStatus.OPEN);
        final Event savedEvent = eventRepository.save(event);
        return eventToEventDTO(savedEvent);
    }

    private Event eventDTOToEvent(EventCreateDTO eventDTO){
        return Event.builder()
                .name(eventDTO.getName())
                .description(eventDTO.getDescription())
                .build();
    }

    private EventCreateDTO eventToEventDTO(Event event){
        return EventCreateDTO.builder()
                .name(event.getName())
                .description(event.getDescription())
                .build();
    }

}
