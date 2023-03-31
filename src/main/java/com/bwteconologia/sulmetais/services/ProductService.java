package com.bwteconologia.sulmetais.services;

import com.bwteconologia.sulmetais.interfaces.IProduct;
import com.bwteconologia.sulmetais.models.ProductModel;
import com.bwteconologia.sulmetais.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProduct {

    ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }
    @Override
    public List<ProductModel> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Optional<ProductModel> findById(int id) {
        return productRepository.findById(id);
    }

    @Override
    public ProductModel save(ProductModel product) {
        return productRepository.save(product);
    }

    @Override
    public Optional<ProductModel> findByProductName(String productName) {
        return productRepository.findByProductName(productName);
    }

    @Override
    public void deleteById(int id) {
        productRepository.deleteById(id);
    }
}
