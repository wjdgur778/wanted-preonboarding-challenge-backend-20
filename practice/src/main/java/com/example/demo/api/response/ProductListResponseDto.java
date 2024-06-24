package com.example.demo.api.response;

import com.example.demo.db.entity.Product;
import com.example.demo.db.entity.p_state;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProductListResponseDto {
    public Long productId;
    public Long productPrice;
    public String productName;
    public String description;
    public p_state productState;
    public static ProductListResponseDto from(Product product){
        return new ProductListResponseDto(product.getProductId(),product.getProductPrice(),product.getProductName(), product.getDescription(), product.getProductState());
    }
}
