package com.example.demo.api.response;

import com.example.demo.db.BaseTimeEntity;
import com.example.demo.db.entity.Product;
import com.example.demo.db.entity.p_state;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

@Builder
@Getter
public class ProductResponseDto  {
    private Long productId;
    private String productName;
    private Long productPrice;
    // 목록 조회기 때문에 설명은 생략
    //    private String description;
    private p_state productState;
    private LocalDateTime reservationTime; // 예약한 시간
//    private LocalDateTime purchaseTime; // 구매 완료된 시간
    public static ProductResponseDto from(Product product){
        return ProductResponseDto.builder()
                .productId(product.getProductId())
                .productState(product.getProductState())
                .productName(product.getProductName())
                .reservationTime(product.getRegisterationDate())
                .productPrice(product.getProductPrice())
                .build();
    }
}
