package com.bwteconologia.sulmetais.interfaces;



import com.bwteconologia.sulmetais.models.ProductModel;

import java.util.List;
import java.util.Optional;

public interface IProduct {
    List<ProductModel> getAllProducts();

    Optional<ProductModel> findById(int id);

    ProductModel save(ProductModel product);
    Optional<ProductModel> findByProductName(String productName);

    void deleteById(int id);
}
