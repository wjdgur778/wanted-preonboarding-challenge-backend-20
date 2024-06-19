package com.example.demo.api.controller;

import com.example.demo.api.service.TransactionService;
import com.example.demo.common.model.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/transaction")
public class TransactionController {
    final private TransactionService transactionService;
    /*
        [요구사항]
        - 구매자(회원인 상태)가 제품의 상세페이지에서 구매하기 버튼을 누르면 거래가 시작됩니다.
        - 판매자와 구매자는 제품의 상세정보를 조회하면 당사자간의 거래내역을 확인할 수 있습니다.
        - 모든 사용자는 내가 "구매한 용품(내가 구매자)"과 "예약중인 용품(내가 구매자/판매자 모두)"의 목록을 확인할 수 있습니다.
        - 판매자는 거래 진행 중인 구매자에 대해 '판매승인'을 하는 경우 거래가 완료됩니다.
     */

    //구매 하기(구매 시작)
    /*
         구매 하기(구매 시작)
         1. (회원)만 구매 가능하다
         2. 상품 id와 회원의 정보가 넘어온다.
     */
    @PostMapping("/create/{productId}")
    public ResponseEntity<Result> create(Authentication authentication, @PathVariable Long productId) {
        String userEmail = authentication.getName();

        return ResponseEntity.status(200).body(Result.builder()
                .message("구매 요청 (예약)")
                .Data(transactionService.create(productId, userEmail))
                .build());
    }

    //판매 승인(구매 완료)
    @PostMapping("/approval")
    public void approval() {

    }

    //예약하기
    @GetMapping("/reserve")
    public void reserve() {

    }
}
