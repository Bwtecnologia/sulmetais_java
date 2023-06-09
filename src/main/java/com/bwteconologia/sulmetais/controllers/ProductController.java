package com.bwteconologia.sulmetais.controllers;


import com.bwteconologia.sulmetais.exceptions.ColorNotFoundException;
import com.bwteconologia.sulmetais.exceptions.GroupNotFoundException;
import com.bwteconologia.sulmetais.exceptions.ProductNotFoundException;
import com.bwteconologia.sulmetais.exceptions.UnitNotFoundException;
import com.bwteconologia.sulmetais.models.*;
import com.bwteconologia.sulmetais.services.ColorService;
import com.bwteconologia.sulmetais.services.GroupService;
import com.bwteconologia.sulmetais.services.ProductService;
import com.bwteconologia.sulmetais.services.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    ProductService productService;
    @Autowired
    private UnitService unitService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private ColorService colorService;

    @GetMapping(value = "/products")
    public ResponseEntity<List<ProductModel>> getAllProducts() {
        List<ProductModel> products = productService.getAllProducts();
        for (ProductModel product : products){
            UnitModel unit = product.getUnit();
            if(unit != null){
                product.setUnit(unit);
            }
        }
        return ResponseEntity.ok().body(products);
    }

    @GetMapping(value = "/products/{id}")
    public ResponseEntity<ProductModel> findById(@PathVariable("id") int id) {
        ProductModel product = productService.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with " + id + " is Not Found!"));
        return ResponseEntity.ok(product);
    }

    @PutMapping(value = "/products/{id}")
    public ResponseEntity<ProductModel> updateProductWithUnitAndGroup(@PathVariable Long id, @RequestBody ProductModel product, @RequestParam Long unitId, @RequestParam Long groupId) {
        Optional<UnitModel> unitOptional = unitService.findById(Math.toIntExact(unitId));
        Optional<GroupModel> groupOptional = groupService.findById(Math.toIntExact(groupId));

        if (!unitOptional.isPresent() || !groupOptional.isPresent()) {
            throw new ProductNotFoundException("Unit or Group not found");
        }

        UnitModel unit = unitOptional.get();
        GroupModel group = groupOptional.get();

        Optional<ProductModel> productOptional = productService.findById(Math.toIntExact(id));

        if (!productOptional.isPresent()) {
            throw new ProductNotFoundException("Product not found");
        }
        ProductModel existingProduct = productOptional.get();
        existingProduct.setUnit(unit);
        existingProduct.setGroup(group);
        existingProduct.setProductName(product.getProductName());
        existingProduct.setProductBuy(product.getProductBuy());
        existingProduct.setProductFabricated(product.getProductFabricated());
        existingProduct.setProductGeneric(product.getProductGeneric());
        existingProduct.setProductPrimary(product.getProductPrimary());
        existingProduct.setProductIntermediary(product.getProductIntermediary());
        existingProduct.setProductFinal(product.getProductFinal());
        existingProduct.setProductPrice(product.getProductPrice());
        existingProduct.setUpdatedAt(new Date());

        ProductModel updatedProduct = productService.save(existingProduct);

        return ResponseEntity.ok(updatedProduct);
    }


    @DeleteMapping(value = "/products/{id}")
    public ResponseEntity<ResponseObjectModel> deleteProduct(@PathVariable("id") Long id) {
        ProductModel product = productService.findById(Math.toIntExact(id))
                .orElseThrow(() -> new ProductNotFoundException("Product with " + id + " is Not Found!"));
        productService.deleteById(product.getId());
        ResponseObjectModel responseObjectModel = new ResponseObjectModel(id, "Product with ID :" + id + " is deleted");
        return ResponseEntity.ok(responseObjectModel);
    }

    @PostMapping(value = "/products")
    public ResponseEntity<ProductModel> createProductWithUnitAndGroup(@RequestBody ProductModel product) {
        Optional<UnitModel> unitOptional = unitService.findById(Math.toIntExact(product.getUnit().getId()));
        Optional<GroupModel> groupOptional = groupService.findById(Math.toIntExact(product.getGroup().getId()));
        Optional<ColorModel> colorOptional = colorService.findById(Math.toIntExact(product.getColor().getId()));

        if (!unitOptional.isPresent()) {
            throw new UnitNotFoundException("Unit not found");
        }
        if (!groupOptional.isPresent()) {
            throw new GroupNotFoundException("Group not found");
        }
        if (!colorOptional.isPresent()) {
            throw new ColorNotFoundException("Color not found");
        }

        UnitModel unit = unitOptional.get();
        GroupModel group = groupOptional.get();
        ColorModel color = colorOptional.get();

        product.setUnit(unit);
        product.setGroup(group);
        product.setColor(color);

        ProductModel savedProduct = productService.save(product);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

}
