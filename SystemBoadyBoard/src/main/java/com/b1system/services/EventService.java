package com.b1system.services;

import com.b1system.models.Event;
import com.b1system.models.EventDTO;
import com.b1system.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventService(final EventRepository eventRepository){
        this.eventRepository = eventRepository;
    }

    public EventDTO create(final EventDTO eventDTO){
        final Event event = eventDTOToEvent(eventDTO);
        final Event savedEvent = eventRepository.save(event);
        return eventToEventDTO(savedEvent);
    }

    private Event eventDTOToEvent(EventDTO eventDTO){
        return Event.builder()
                .name(eventDTO.getName())
                .description(eventDTO.getDescription())
                .status(eventDTO.getStatus()).build();
    }

    private EventDTO eventToEventDTO(Event event){
        return EventDTO.builder()
                .name(event.getName())
                .description(event.getDescription())
                .status(event.getStatus()).build();
    }

}
