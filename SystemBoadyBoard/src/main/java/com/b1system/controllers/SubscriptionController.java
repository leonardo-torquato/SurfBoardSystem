package com.b1system.controllers;

import com.b1system.models.createDtos.SubscriptionCreateDTO;
import com.b1system.services.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subscription")
@CrossOrigin("*")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @Autowired
    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @GetMapping("/")
    public String hello(){
        return "Subscription";
    }

    @PostMapping("/create")
    public SubscriptionCreateDTO createSubscription(@Validated @RequestBody SubscriptionCreateDTO body){
        return subscriptionService.create(body);
    }

    //TODO: verificar pagamento

}
