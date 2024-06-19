package com.example.demo.api.response;

import com.example.demo.db.entity.Product;
import com.example.demo.db.entity.Transaction;
import com.example.demo.db.entity.p_state;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Builder
@Getter
public class ProductDetailResponseDto {
    private Long productId;
    private String productName;
    private Long productPrice;
    private String description;
    private p_state productState;
    private LocalDateTime registrationTime; // 제품 등록 시간
    private Transaction transaction;
    private String me;

    public static ProductDetailResponseDto from(Product product,String me,Transaction transaction){
        return ProductDetailResponseDto.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .productPrice(product.getProductPrice())
                .productState(product.getProductState())
                .registrationTime(product.getRegisterationDate())
                .description(product.getDescription())
                .me(me)
                .transaction(transaction)
                .build();
    }
    public static ProductDetailResponseDto from(Product product){
        return ProductDetailResponseDto.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .productPrice(product.getProductPrice())
                .productState(product.getProductState())
                .registrationTime(product.getRegisterationDate())
                .description(product.getDescription())
                .build();
    }
}
