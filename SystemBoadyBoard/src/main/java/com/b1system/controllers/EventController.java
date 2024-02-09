package com.b1system.controllers;

import com.b1system.models.createDtos.EventCreateDTO;
import com.b1system.models.dtos.EventDTO;
import com.b1system.services.EventService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/event")
@CrossOrigin("*")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("/create")
    public EventDTO createEvent(@Validated @RequestBody EventCreateDTO body){
        return eventService.create(body);
    }

    @GetMapping("/get")
    public List<EventDTO> createEvent(){
        return eventService.getAll();
    }

}
