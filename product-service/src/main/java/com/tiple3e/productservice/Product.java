package com.tiple3e.productservice;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Product {
    @Id
    private String id;

    private String name;

    private Integer price;

    private Integer quantity;
}
