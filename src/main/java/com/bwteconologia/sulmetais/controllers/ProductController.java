package com.bwteconologia.sulmetais.controllers;


import com.bwteconologia.sulmetais.exceptions.ProductAlreadyExistsException;
import com.bwteconologia.sulmetais.exceptions.ProductNotFoundException;
import com.bwteconologia.sulmetais.models.ProductModel;
import com.bwteconologia.sulmetais.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping(value = "/products")
    public ResponseEntity<List<ProductModel>> getAllProducts() {
        List<ProductModel> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping(value = "/products/{id}")
    public ResponseEntity<ProductModel> findById(@PathVariable("id") int id) {
        ProductModel product = productService.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with " + id + " is Not Found!"));
        return ResponseEntity.ok(product);
    }

    @PostMapping(value = "/product")
    public ResponseEntity<ProductModel> addProduct(@RequestBody ProductModel product) {
        Optional<ProductModel> existsProduct = productService.findByProductName(product.getProductName());
        if (existsProduct.isPresent()) {
            throw new ProductAlreadyExistsException("Product already exists");
        }
        ProductModel savedProduct = productService.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    @PutMapping(value = "/product/{id}")
    public ResponseEntity<ProductModel> updateProduct(@PathVariable("id") int id, @RequestBody ProductModel productUpdated) {
        ProductModel product = productService.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Group with " + id + " is Not Found!"));
        product.setProductName(productUpdated.getProductName());
        product.setProductBuy(productUpdated.getProductBuy());
        product.setProductPrimary(productUpdated.getProductPrimary());
        product.setProductIntermediary(productUpdated.getProductIntermediary());
        product.setProductFinal(productUpdated.getProductFinal());
        product.setProductPrice(productUpdated.getProductPrice());
        product.setUpdatedAt(new Date());

        ProductModel updatedProduct = productService.save(product);
        return ResponseEntity.ok(updatedProduct);
    }
    @DeleteMapping(value = "/products/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") int id) {
        ProductModel product = productService.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with " + id + " is Not Found!"));
        productService.deleteById(product.getId());
        return ResponseEntity.ok("Product with ID :" + id + " is deleted");
    }

}
