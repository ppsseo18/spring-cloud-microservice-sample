package com.triple3e.orderservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;


@Component
@EnableBinding(OrderingSource.class)
public class EventPublisher {
    @Autowired
    private OrderingSource orderingSource;

    public void publish(String msg) {
        orderingSource.orderQ().send(message(msg));
    }

    private static final <T> Message<T> message(T val) {
        return MessageBuilder.withPayload(val).build();
    }
}
