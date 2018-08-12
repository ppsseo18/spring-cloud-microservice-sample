package com.triple3e.orderservice;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "userOrder")
public class Order {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    Integer id;

    String userId;

    String productId;

    Integer quantity;

    Boolean complete;

    public Order(String userId, String productId, Integer quantity, Boolean complete){
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
        this.complete = complete;
    }
}
