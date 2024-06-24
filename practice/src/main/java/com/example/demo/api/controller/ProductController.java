package com.example.demo.api.controller;

import com.example.demo.api.request.RegistProductRequestDto;
import com.example.demo.api.response.ProductDetailResponseDto;
import com.example.demo.api.response.ProductResponseDto;
import com.example.demo.api.service.ProductService;
import com.example.demo.common.model.Result;

import com.example.demo.db.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {
    final private ProductService productService;

    //상품 등록
    //회원만 등록가능
    //authenticaion을 통해 현재 이 물품을 등록하려고 하는 유저의 정보를 알아낼 수 있다.
    @PostMapping("/regist")
    public ResponseEntity<Result> registProduct(Authentication authentication, @RequestBody RegistProductRequestDto registProductRequestDto) {
        return ResponseEntity.status(200).body(Result.builder()
                .Data(productService.regist(registProductRequestDto, authentication.getName()))
                .message("제품 등록 성공")
                .build()
        );
    }

    //(비회원도 요청 가능)제품 리스트 조회
    @GetMapping
    public ResponseEntity<Result> getProductList() {
        List<ProductResponseDto> productList = productService.getProductList();
        return ResponseEntity.status(200).body(Result.builder()
                .Data(productList)
                .message("제품 조회 성공")
                .build());
    }

    //회원이 요청한 물품 상세정보
    //판매자 혹은 구매자가 물품을 상세조회 한 경우 당사자간의 거래내역을 확인 할수있다.
    //거래내역 -> 거래된 시간, 누구와 거래했는지,
    //이때 내가 상세 조회하는 물품의 판매자 혹은 구매자여야 거래내역을 확인할 수 있다.
    //타인의 구매용품을 조회하는 경우를 예외 처리

    //또한 비회원과 화원을 구분하여 메소드도 구분해야할지, 하나의 메소드로 authentication이 있는지의 여부로 확인할지
    //이때는 authencication 해더만 있고 인증이 안된 회원도 존재할 수있다.

    //@Nullable을 통해서 인증 헤더가 있어도 되고 없어도 되게함
    @GetMapping("/detail/{productId}")
    public ResponseEntity<Result> getProductList(@Nullable Authentication authentication, @PathVariable Long productId) {
        System.out.println("제품 상세 조회");
        //회원인 경우
        //접근제한 확인
        if (authentication != null && authentication.isAuthenticated()) {
            //회원확인
            System.out.println(authentication.getName());
            ProductDetailResponseDto product = productService.getProductDetailForUser(productId, authentication.getName());
            return ResponseEntity.status(200).body(Result.builder()
                    .Data(product)
                    .message("회원 제품 상세 조회 성공")
                    .build());
        }
        //비회원인경우
        else {
            return ResponseEntity.status(200).body(Result.builder()
                    .Data(productService.getProductDetail(productId))
                    .message("판매자와 구매자를 제외한 상세조회")
                    .build());
        }
    }

    //구매한 물품 리스트
    @GetMapping("/purchasedlist")
    public ResponseEntity<Result> getpurchasedProductList(Authentication authentication) {
        String userEmail = authentication.getName();

        return ResponseEntity.status(200).body(Result.builder()
                .Data(productService.getPurchasedProductList(userEmail))
                .message("구매한 물품 리스트 조회")
                .build());
    }
    //예약한 물품 리스트
    @GetMapping("/reservedlist")
    public ResponseEntity<Result> getReservedProductList(Authentication authentication) {
        String userEmail = authentication.getName();

        return ResponseEntity.status(200).body(Result.builder()
                .Data(productService.getReservedProductList(userEmail))
                .message("판매자와 구매자를 제외한 상세조회")
                .build());
    }
}


