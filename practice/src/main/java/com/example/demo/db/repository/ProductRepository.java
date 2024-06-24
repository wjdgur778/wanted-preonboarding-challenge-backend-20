package com.example.demo.db.repository;

import com.example.demo.db.entity.Product;
import com.example.demo.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long> {
     Optional<Product> findByProductId(Long productId);
     List<Product> findAllByBuyer_userEmail (String userEmail);
}
