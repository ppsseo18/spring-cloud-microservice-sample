package com.triple3e.userservice;


import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(OrderingSink.class)
public class EventSubscriber {
    @StreamListener(OrderingSink.OrderQ)
    public void subscribe(String content) {
        System.out.println(content);
    }
}
