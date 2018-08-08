package com.triple3e.orderservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class OrderController {

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/")
    public String getOrder() {
        return restTemplate.getForObject("http://user-service/", String.class);
    }
}
