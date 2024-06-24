package com.example.demo.db.entity;

import com.example.demo.db.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

import static com.example.demo.db.entity.p_state.*;
import static com.example.demo.db.entity.t_state.*;

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

    @Enumerated(EnumType.STRING)
    private t_state transactionStatus;

    @Builder
    public Transaction(Long transactionId, User seller, User buyer, Product product, t_state transactionStatus) {
        this.transactionId = transactionId;
        this.seller = seller;
        this.buyer = buyer;
        this.product = product;
        this.transactionStatus = transactionStatus;
    }

    public static Transaction from(User buyer, Product product, t_state transactionStatus) {
        return Transaction.builder()
                .buyer(buyer)
                .seller(product.getSeller())
                .transactionStatus(transactionStatus)
                .product(product)
                .build();
    }

    public void completeTransaction(){
        LocalDateTime currentDateTime = LocalDateTime.now();
        this.transactionStatus = TRADE_COMPLETE;
        this.product.setProductState(CONFIRMED);
        super.setRegisterationDate(currentDateTime);
    }
}
