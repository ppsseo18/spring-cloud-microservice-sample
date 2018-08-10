package com.triple3e.userservice;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class EventSubscriber {

    @RabbitListener(queues = "OrderQ")
    public void subscribe(String content) {
        System.out.println(content);
    }
}
