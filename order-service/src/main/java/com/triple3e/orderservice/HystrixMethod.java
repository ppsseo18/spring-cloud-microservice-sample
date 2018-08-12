package com.triple3e.orderservice;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Component
public class HystrixMethod {

    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getDefaultUser")
    public ResponseEntity<Map> getUser(String userId) {
        URI uri = UriComponentsBuilder.newInstance().scheme("http").host("user-service")
                .path("/users/{id}")
                .build().expand(userId)
                .encode()
                .toUri();
        return restTemplate.getForEntity(uri, Map.class);
    }

    public ResponseEntity<Map> getDefaultUser(String userId) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @HystrixCommand(fallbackMethod = "getDefaultProduct")
    public ResponseEntity<Map> getProduct(String productId) {
        URI uri = UriComponentsBuilder.newInstance().scheme("http").host("product-service")
                .path("/products/{id}")
                .build().expand(productId)
                .encode()
                .toUri();
        return restTemplate.getForEntity(uri, Map.class);
    }

    public ResponseEntity<Map> getDefaultProduct(String productId){
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @HystrixCommand(fallbackMethod = "patchDefaultUserPoints")
    public ResponseEntity<Void> patchUserPoints(String userId, Integer points) {
        URI uri = UriComponentsBuilder.newInstance().scheme("http").host("user-service")
                .path("/users/{id}/points")
                .build().expand(userId)
                .encode()
                .toUri();
        Map<String, Integer> requestBody = new HashMap<>();
        requestBody.put("points", points);
        restTemplate.patchForObject(uri, requestBody, ResponseEntity.class);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Void> patchDefaultUserPoints(String userId, Integer points){
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @HystrixCommand
    public ResponseEntity<Void> patchProductQuantity(String productId, Integer quantity) {
        URI uri = UriComponentsBuilder.newInstance().scheme("http").host("product-service")
                .path("/products/{id}/quantity")
                .build().expand(productId)
                .encode()
                .toUri();
        Map<String, Integer> requestBody = new HashMap<>();
        requestBody.put("quantity", quantity);
        restTemplate.patchForObject(uri, requestBody, ResponseEntity.class);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Void> patchDefaultProductQuantity(String productId, Integer quantity){
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
