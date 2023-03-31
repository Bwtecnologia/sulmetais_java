package com.bwteconologia.sulmetais.controllers;


import com.bwteconologia.sulmetais.exceptions.ProductAlreadyExistsException;
import com.bwteconologia.sulmetais.exceptions.ProductNotFoundException;
import com.bwteconologia.sulmetais.models.ProductModel;
import com.bwteconologia.sulmetais.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<ProductModel> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping(value = "/products/{id}")
    public ProductModel findById(@PathVariable("id") int id) {
        ProductModel product = productService.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with " + id + " is Not Found!"));
        return product;
    }

    @PostMapping(value = "/product")
    public ProductModel addProduct(@RequestBody ProductModel product) {
        Optional<ProductModel> existsProduct = productService.findByProductName(product.getProductName());
        if(existsProduct != null){
            throw new ProductAlreadyExistsException("Product already exists");
        }
        return productService.save(product);
    }

    @PutMapping(value = "/product/{id}")
    public ProductModel updateProduct(@PathVariable("id") int id, @RequestBody ProductModel productUpdated) {
        ProductModel product = productService.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Group with " + id + " is Not Found!"));
        product.setProductName(productUpdated.getProductName());
        product.setProductBuy(productUpdated.getProductBuy());
        product.setProductPrimary(productUpdated.getProductPrimary());
        product.setProductIntermediary(productUpdated.getProductIntermediary());
        product.setProductFinal(productUpdated.getProductFinal());
        product.setProductPrice(productUpdated.getProductPrice());
        product.setUpdatedAt(new Date());

        return productService.save(product);
    }
    @DeleteMapping(value = "/products/{id}")
    public String deleteProducts(@PathVariable("id") int id){
        ProductModel product = productService.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with " + id + " is Not Found!"));
        productService.deleteById(product.getId());
        return "Group with ID :"+id+" is deleted";
    }

}
