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

    private Integer points;

    public User(String id, String name, Integer points) {
        this.id = id;
        this.name = name;
        this.points = points;
    }

}
