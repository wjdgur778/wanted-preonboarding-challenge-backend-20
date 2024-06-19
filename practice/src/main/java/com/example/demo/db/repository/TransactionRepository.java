package com.example.demo.db.repository;


import com.example.demo.db.entity.Transaction;
import com.example.demo.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    //Transaction 엔티티안에 있는 엔티티들의 키로 들어가야한다.
    //그래서 productId가 아닌 product_productId
    Optional<Transaction> findByProduct_ProductIdAndSeller_UserEmail(Long productId, String sellerEmail);
    Optional<Transaction> findByProduct_ProductIdAndBuyer_UserEmail(Long productId,String buyerEmail);
}
