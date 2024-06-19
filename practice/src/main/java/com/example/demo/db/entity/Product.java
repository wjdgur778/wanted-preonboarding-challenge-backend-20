package com.example.demo.db.entity;

import com.example.demo.db.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Product extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String productName;
    private Long productPrice;
    private String description;
    @Enumerated(EnumType.STRING)
    private p_state productState;
    //의미상 sellerId를 그대로 사용하지않고 seller를 사용하게되어 데이터베이스에 직접 맵핑이 어렵다.
    //따라서 JoinColum을 이용해 "seller_id"에 맵핑시키도록 한다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private User seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id")
    private User buyer;
    @Builder
    public Product(Long productId, String productName, Long productPrice, String description, p_state productState, User seller, User buyer) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.description = description;
        this.productState = productState;
        this.seller = seller;
        this.buyer = buyer;
    }

}

