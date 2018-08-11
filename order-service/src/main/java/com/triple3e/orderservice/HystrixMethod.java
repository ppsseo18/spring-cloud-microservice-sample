package com.triple3e.orderservice;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class HystrixMethod {

    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getDefaultUser")
    public String getUser() {
        String user = restTemplate.getForObject("http://user-service/", String.class);

        return user;
    }

    public String getDefaultUser(){
        String user = "user not found";
        return user;
    }

}
