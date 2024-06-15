package com.example.demo.db.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Product {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String productName;
    private Long productPrice;
    private String description;
    @Enumerated(EnumType.STRING)
    private p_state productState;

    @ManyToOne(fetch = FetchType.LAZY)
    private User sellerId;
    @ManyToOne(fetch = FetchType.LAZY)
    private User buyerId;
}
