package com.triple3e.userservice;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

@Component
public interface OrderingSink {
    String OrderQ = "orderQ";

    @Input(OrderQ)
    MessageChannel orderQ();
}
