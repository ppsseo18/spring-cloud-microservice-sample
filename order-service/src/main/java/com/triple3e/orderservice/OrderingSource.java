package com.triple3e.orderservice;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

@Component
public interface OrderingSource {
    String OrderQ="orderQ";

    @Output(OrderQ)
    MessageChannel orderQ();
}
