package com.example.demo.db.entity;

import com.example.demo.db.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Transaction extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private User seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id")
    private User buyer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false)
    private LocalDateTime transactionTime;

    @Enumerated(EnumType.STRING)
    private t_state transactionStatus;

    @Builder
    public Transaction(Long transactionId, User seller, User buyer, Product product, LocalDateTime transactionTime, t_state transactionStatus) {
        this.transactionId = transactionId;
        this.seller = seller;
        this.buyer = buyer;
        this.product = product;
        this.transactionTime = transactionTime;
        this.transactionStatus = transactionStatus;
    }

    public static Transaction from(User buyer, Product product, t_state transactionStatus) {
        return Transaction.builder()
                .buyer(buyer)
                .transactionStatus(transactionStatus)
                .product(product)
                .build();
    }
}
