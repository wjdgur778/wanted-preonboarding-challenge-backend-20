package com.example.demo.api.service;

import com.example.demo.api.request.RegistProductRequestDto;
import com.example.demo.api.response.ProductDetailResponseDto;
import com.example.demo.api.response.ProductListResponseDto;
import com.example.demo.api.response.ProductResponseDto;
import com.example.demo.common.exception.RestApiException;
import com.example.demo.db.entity.Product;
import com.example.demo.db.entity.Transaction;
import com.example.demo.db.entity.User;
import com.example.demo.db.repository.ProductRepository;
import com.example.demo.db.repository.TransactionRepository;
import com.example.demo.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.demo.common.exception.CommonErrorCode.*;
import static com.example.demo.db.entity.p_state.*;

@Service
@RequiredArgsConstructor
public class ProductService {
    final private ProductRepository productRepository;
    final private UserRepository userRepository;
    final private TransactionRepository transactionRepository;
    //상품 등록
    //회원만 등록가능
    @Transactional
    public Product regist(RegistProductRequestDto registProductRequestDto, String sellerEmail){
        User seller = userRepository.findByUserEmail(sellerEmail)
                .orElseThrow(()->new RestApiException(MEMBER_NOT_FOUND));

        Product registProduct = Product.builder()
                .productPrice(registProductRequestDto.getProductPrice())
                .productName(registProductRequestDto.getProductName())
                .productState(NEW)
                .description(registProductRequestDto.getDescription())
                .seller(seller)
                .build();

        return productRepository.save(registProduct);
    }

    //[(모든 유저 가능) 제품 목록 조회]
    public List<ProductResponseDto> getProductList(){
        List<ProductResponseDto> result = productRepository.findAll().stream()
                .map(product -> ProductResponseDto.from(product)
                ).collect(Collectors.toList());
        return result;
    }

    //[회원이 요청한 물품 상세정보]

    //판매자 혹은 구매자가 물품을 상세조회 한 경우 당사자간의 거래내역을 확인 할수있다.
    //거래내역 -> 거래된 시간, 누구와 거래했는지,
    //이때 내가 상세 조회하는 물품의 판매자 혹은 구매자여야 거래내역을 확인할 수 있다.
    //타인의 구매용품을 조회하는 경우를 예외 처리
    public ProductDetailResponseDto getProductDetailForUser(Long productId, String userEmail){
        Product product = productRepository.findByProductId(productId)
                .orElseThrow(()->new RestApiException(PRODUCT_NOT_FOUND));
        System.out.println("getProductDetailForUser");
        Transaction transaction = transactionRepository.findByProduct_ProductIdAndSeller_UserEmail(productId,userEmail)
                .orElseThrow(()->new RestApiException(TRANSACTION_NOT_FOUND));

        // 특정 권한에 따라 데이터 필터링
        // 회원이고 판매자인 경우의 거래 내역 조회
        if (product.getSeller().getUserEmail().equals(userEmail)){
            System.out.println("getProductDetailForUser : 판매자의 경우");
            // 거래 내역 조회 로직 추가
            return ProductDetailResponseDto.from(product,"seller",transaction);
        }
        // 회원이고 구매자인 경우의 거래 내역 조회
        else if (product.getBuyer().getUserEmail().equals(userEmail)){
            // 거래 내역 조회 로직 추가
            return ProductDetailResponseDto.from(product,"seller",transaction);
        }
        // 판매자도 구매자도 아닌 경우
        // 거래내역 조회를 하지 않는다.
        else
            return ProductDetailResponseDto.from(product);
    }



    //비회원이 요청한 물품 상세정보
    public ProductDetailResponseDto getProductDetail(Long productId){
        Product product = productRepository.findByProductId(productId)
                .orElseThrow(()->new RestApiException(PRODUCT_NOT_FOUND));
        return ProductDetailResponseDto.from(product);
    }

    //구매한 상품 목록 조회
    public List<ProductListResponseDto> getPurchasedProductList(String userEmail){
         return productRepository.findAllByBuyer_userEmail(userEmail).stream()
                .filter(product -> {
                    if(product.getProductState().equals(CONFIRMED))
                    return true;
                    return false;
                }).map(product -> ProductListResponseDto.from(product))
                 .collect(Collectors.toList());
    }

    //예약한 상품 목록
    public List<ProductListResponseDto> getReservedProductList(String userEmail){
        return productRepository.findAllByBuyer_userEmail(userEmail).stream()
                .filter(product -> {
                    if(product.getProductState().equals(RESERVED))
                        return true;
                    return false;
                }).map(product -> ProductListResponseDto.from(product))
                .collect(Collectors.toList());
    }


}
