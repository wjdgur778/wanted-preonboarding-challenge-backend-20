package com.example.demo.api.service;

import com.example.demo.api.response.TransactionResponseDto;
import com.example.demo.common.exception.RestApiException;
import com.example.demo.db.entity.*;
import com.example.demo.db.repository.ProductRepository;
import com.example.demo.db.repository.TransactionRepository;
import com.example.demo.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.demo.common.exception.CommonErrorCode.*;
import static com.example.demo.db.entity.p_state.*;
import static com.example.demo.db.entity.t_state.*;

@Service
@RequiredArgsConstructor
public class TransactionService {
    final private TransactionRepository transactionRepository;
    final private UserRepository userRepository;
    final private ProductRepository productRepository;
    /*
        구매(예약)를 시작하기에 앞서
        회원이 판매자는 아닌지,
        예약 상태에 있는 상품은 아닌지
        구매 과정에서 가격의 정보가 내가 요청한 정보와 동일한지
     */
    @Transactional
    public TransactionResponseDto create(Long productId, String userEmail){
        Product product = productRepository.findByProductId(productId)
                .orElseThrow(()->new RestApiException(PRODUCT_NOT_FOUND));
        User buyer = userRepository.findByUserEmail(userEmail)
                .orElseThrow(()->new RestApiException(MEMBER_NOT_FOUND));
        //판매자면 허용 x
        if(product.getSeller().getUserEmail().equals(userEmail)){
            throw new RestApiException(BAD_REQUEST);
        }
        if(product.getProductState().equals(RESERVED)){
            throw new RestApiException(BAD_REQUEST);
        }
        //transaction을 save할때 product를 업데이트 한다.
        //업데이트한 product와 seller, buyer를 넣어 저장
        product.setProductState(RESERVED);
        product.setBuyer(buyer);

        //save한 entity 자체를 반환하면 안됨
        //lazy 로딩한 객체가 실제 객체가 아닌 Proxy가 담겨있기 때문에 직렬화 하는 과정에서 오류가 생김
        return TransactionResponseDto.from(transactionRepository.save(Transaction.from(buyer,product, TRADING)));
    }

    /*
        구매 확정 하기
        판매자만이 확정할 수 있다.
     */
    @Transactional
    public TransactionResponseDto approval(Long transactionId,String userEmail){
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(()->new RestApiException(TRANSACTION_NOT_FOUND));
        //판매자만이 확정할 수 있다.
        if(!transaction.getSeller().getUserEmail().equals(userEmail)){
            throw new RestApiException(BAD_REQUEST);
        }
        //이미 확정된 거래면 안됌
        if(transaction.getTransactionStatus().equals(TRADE_COMPLETE)){
            throw new RestApiException(BAD_REQUEST);
        }
        //거래 내역에 해당하는 transaction과 product의 상태를 업데이트 한다.
        transaction.completeTransaction();
        //save한 entity 자체를 반환하면 안됨
        return TransactionResponseDto.from(transaction);
    }

}
