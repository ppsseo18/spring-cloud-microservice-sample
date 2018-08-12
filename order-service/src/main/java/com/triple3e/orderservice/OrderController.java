package com.triple3e.orderservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class OrderController {

    @Autowired
    HystrixMethod hystrixMethod;

    @Autowired
    EventPublisher eventPublisher;
    
    @Autowired
    OrderRepository orderRepository;

    @GetMapping("/send")
    public String sendOrder() {
        eventPublisher.publish("send order!");
        return "success";
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<?> getProducts(@PathVariable(value = "id") String id) {
        return hystrixMethod.getUser(id);
    }

    @GetMapping("/orders")
    public Iterable<Order> getOrder() {
        return orderRepository.findAll();
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable(value = "id") Integer id) {
        return orderRepository.findById(id).map(order -> ResponseEntity.ok(order)).orElse(new ResponseEntity(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/orders")
    public ResponseEntity<Order> createOrder(@RequestBody Order requestOrder) {
        ResponseEntity<Map> product = hystrixMethod.getProduct(requestOrder.getProductId());
        if(product.getStatusCode() == HttpStatus.NOT_FOUND){
            System.out.println("product exception");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ResponseEntity<Map> user = hystrixMethod.getUser(requestOrder.getUserId());
        if(user.getStatusCode() == HttpStatus.NOT_FOUND || ((Integer)user.getBody().get("points")).intValue() < ((Integer)product.getBody().get("price")).intValue() * requestOrder.getQuantity()){
            System.out.println("user exception");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ResponseEntity<Void> patchUserResponse = hystrixMethod.patchUserPoints(requestOrder.getUserId(), -((Integer)product.getBody().get("price")) * requestOrder.getQuantity());
        if(patchUserResponse.getStatusCode() == HttpStatus.NOT_FOUND){
            System.out.println("user patch exception");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ResponseEntity<Void> patchProductResponse = hystrixMethod.patchProductQuantity(requestOrder.getProductId(), -(requestOrder.getQuantity()));
        if(patchProductResponse.getStatusCode() == HttpStatus.NOT_FOUND){
            System.out.println("product patch exception");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(orderRepository.save(requestOrder));
    }

    @PutMapping("/orders/{id}")
    public ResponseEntity<Void> updateOrder(@PathVariable(value = "id") Integer id, @RequestBody Order requestOrder) {
        return orderRepository.findById(id).map(order -> {
            order.setUserId(requestOrder.getUserId());
            order.setProductId(requestOrder.getProductId());
            order.setQuantity(requestOrder.getQuantity());
            order.setComplete(requestOrder.getComplete());
            orderRepository.save(order);
            return new ResponseEntity(HttpStatus.OK);
        }).orElse(new ResponseEntity(HttpStatus.NOT_FOUND));
    }

    @PatchMapping("/orders/{id}/complete")
    public ResponseEntity<Void> updateOrderPoints(@PathVariable(value = "id") Integer id, @RequestBody Map<String, Boolean> requestBody) {
        return orderRepository.findById(id).map(order -> {
            order.setComplete(requestBody.get("complete"));
            orderRepository.save(order);
            return new ResponseEntity(HttpStatus.OK);
        }).orElse(new ResponseEntity(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("orders/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable(value = "id") Integer id) {
        return orderRepository.findById(id).map(order -> {
            orderRepository.delete(order);
            return new ResponseEntity(HttpStatus.OK);
        }).orElse(new ResponseEntity(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("orders/{id}/complete")
    public ResponseEntity<Void> deleteOrderPoints(@PathVariable(value = "id") Integer id, @RequestBody Map<String, Boolean> requestBody) {
        return orderRepository.findById(id).map(order -> {
            order.setComplete(requestBody.get("complete"));
            orderRepository.save(order);
            return new ResponseEntity(HttpStatus.OK);
        }).orElse(new ResponseEntity(HttpStatus.NOT_FOUND));
    }

}
