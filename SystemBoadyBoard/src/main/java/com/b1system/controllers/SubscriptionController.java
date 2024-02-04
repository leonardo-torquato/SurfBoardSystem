package com.b1system.controllers;

import com.b1system.models.SubscriptionDTO;
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
    public SubscriptionDTO createSubscription(@Validated @RequestBody SubscriptionDTO body){
        return subscriptionService.create(body);
    }

    //TODO: verificar pagamento

}
