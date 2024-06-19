package com.example.demo.api.service;

import com.example.demo.common.exception.CommonErrorCode;
import com.example.demo.common.exception.RestApiException;
import com.example.demo.db.entity.*;
import com.example.demo.db.repository.ProductRepository;
import com.example.demo.db.repository.TransactionRepository;
import com.example.demo.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public Transaction create(Long productId, String userEmail){
        Product product = productRepository.findByProductId(productId)
                .orElseThrow(()->new RestApiException(CommonErrorCode.PRODUCT_NOT_FOUND));
        User buyer = userRepository.findByUserEmail(userEmail)
                .orElseThrow(()->new RestApiException(CommonErrorCode.MEMBER_NOT_FOUND));
        //판매자면 허용 x
        if(product.getSeller().getUserEmail().equals(userEmail)){
            throw new RestApiException(CommonErrorCode.BAD_REQUEST);
        }
        //transaction을 save할때 product를 업데이트 한다.
        //업데이트한 prodcut와 seller, buyer를 넣어 저장
        product.setProductState(p_state.RESERVED);

        return transactionRepository.save(Transaction.from(buyer,product, TRADING));
    }
}
