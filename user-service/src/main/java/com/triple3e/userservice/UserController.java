package com.triple3e.userservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/users")
    public Iterable<User> getUser() {
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable(value = "id") String id) {
        return userRepository.findById(id).map(user -> ResponseEntity.ok(user)).orElse(new ResponseEntity(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/users")
    public User createUser(@RequestBody User requestUser) {
        return userRepository.findById(requestUser.getId()).orElse(userRepository.save(requestUser));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable(value = "id") String id, @RequestBody User requestUser) {
        return userRepository.findById(id).map(user -> {
            user.setName(requestUser.getName());
            user.setPoints(requestUser.getPoints());
            userRepository.save(user);
            return new ResponseEntity(HttpStatus.OK);
        }).orElse(new ResponseEntity(HttpStatus.NOT_FOUND));
    }

    @PatchMapping("/users/{id}/points")
    public ResponseEntity<Void> updateUserPoints(@PathVariable(value = "id") String id, @RequestBody Map<String, Integer> requestBody) {
        return userRepository.findById(id).map(user -> {
            user.setPoints(user.getPoints() + requestBody.get("points"));
            userRepository.save(user);
            return new ResponseEntity(HttpStatus.OK);
        }).orElse(new ResponseEntity(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable(value = "id") String id) {
        return userRepository.findById(id).map(user -> {
            userRepository.delete(user);
            return new ResponseEntity(HttpStatus.OK);
        }).orElse(new ResponseEntity(HttpStatus.NOT_FOUND));
    }

}
