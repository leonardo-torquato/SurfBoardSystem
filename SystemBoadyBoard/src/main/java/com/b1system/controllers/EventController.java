package com.b1system.controllers;

import com.b1system.models.EventDTO;
import com.b1system.services.EventService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/event")
@CrossOrigin("*")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("/create")
    public EventDTO createEvent(@Validated @RequestBody EventDTO body){
        return eventService.create(body);
    }

}
