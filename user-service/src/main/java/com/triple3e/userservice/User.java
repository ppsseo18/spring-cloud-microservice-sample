package com.triple3e.userservice;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class User {

    @Id
    private String id;

    private String name;

    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }

}
