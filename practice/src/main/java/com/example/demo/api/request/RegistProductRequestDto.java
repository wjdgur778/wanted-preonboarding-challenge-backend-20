package com.example.demo.api.request;

import com.example.demo.db.entity.p_state;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegistProductRequestDto {
    private String productName;
    private Long productPrice;
    private String description;
    private p_state productState;
}
