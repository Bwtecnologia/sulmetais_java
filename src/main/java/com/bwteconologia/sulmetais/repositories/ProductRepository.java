package com.bwteconologia.sulmetais.repositories;

import com.bwteconologia.sulmetais.models.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ProductRepository extends JpaRepository<ProductModel, Integer> {
    Optional<ProductModel> findByProductName(String productName);
}
