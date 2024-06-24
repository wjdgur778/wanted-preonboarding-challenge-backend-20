package com.example.demo.api.response;

import com.example.demo.db.entity.Product;
import com.example.demo.db.entity.Transaction;
import com.example.demo.db.entity.User;
import com.example.demo.db.entity.t_state;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class TransactionResponseDto {
    private Long transactionId;


    public TransactionResponseDto(Long transactionId) {
        this.transactionId = transactionId;
    }

    public static TransactionResponseDto from(Transaction transaction) {
        return new TransactionResponseDto(transaction.getTransactionId());
    }

}
