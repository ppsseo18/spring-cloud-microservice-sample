package com.triple3e.userservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class UserController {

    @Value("${server.port}")
    String port;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/")
    public String userHome() {
        return port;
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable(value = "id") String id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException());
    }

    @PostMapping("/users/")
    public User createUser(@RequestBody User requestUser) {
        return userRepository.findById(requestUser.getId()).orElse(userRepository.save(requestUser));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@PathVariable(value = "id") String id, @RequestBody User requestUser) {
        return userRepository.findById(id).map(user -> {
            user.setName(requestUser.getName());
            user.setPoints(requestUser.getPoints());
            userRepository.save(user);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException());
    }

    @PatchMapping("/users/{id}/points")
    public ResponseEntity<?> updateUserPoints(@PathVariable(value = "id") String id, @RequestBody Map<String, Integer> requestBody) {
        return userRepository.findById(id).map(user -> {
            user.setPoints(user.getPoints() - requestBody.get("points"));
            userRepository.save(user);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException());
    }

    @DeleteMapping("users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "id") String id) {
        return userRepository.findById(id).map(user -> {
            userRepository.delete(user);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException());
    }

    @DeleteMapping("users/{id}/points")
    public ResponseEntity<?> deleteUserPoints(@PathVariable(value = "id") String id, @RequestBody Map<String, Integer> requestBody) {
        return userRepository.findById(id).map(user -> {
            user.setPoints(user.getPoints() - requestBody.get("points"));
            userRepository.save(user);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException());
    }


}
