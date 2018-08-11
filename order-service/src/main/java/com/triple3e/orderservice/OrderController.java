package com.triple3e.orderservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    HystrixMethod hystrixMethod;

    @Autowired
    EventPublisher eventPublisher;

    @GetMapping("/")
    public String getOrder() {
        return hystrixMethod.getUser();
    }

    @GetMapping("/send")
    public String sendOrder() {
        eventPublisher.publish("send order!");
        return "success";
    }
}
