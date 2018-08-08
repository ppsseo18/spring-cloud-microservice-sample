package com.triple3e.userservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Value("${server.port}")
    String port;

    @GetMapping("/")
    public String userHome() {
        return port;
    }
}
