package com.bwteconologia.sulmetais.controllers;


import com.bwteconologia.sulmetais.exceptions.ColorNotFoundException;
import com.bwteconologia.sulmetais.exceptions.GroupNotFoundException;
import com.bwteconologia.sulmetais.exceptions.ProductNotFoundException;
import com.bwteconologia.sulmetais.exceptions.UnitNotFoundException;
import com.bwteconologia.sulmetais.exceptions.group_color.GroupColorNotExistsException;
import com.bwteconologia.sulmetais.models.*;
import com.bwteconologia.sulmetais.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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

    @Autowired
    private GroupColorService groupColorService;

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
    public ResponseEntity<ProductModel> updateProductWithUnitAndGroup(@PathVariable Long id, @RequestBody ProductModel product,
                                                                      @RequestParam Long unitId, @RequestParam(required = false) Long groupId) {
        Optional<UnitModel> unitOptional = unitService.findById(Math.toIntExact(unitId));

        Set<GroupColorModel> groupColorModelList = new HashSet<>();
        //verify if groupcolor exist in groupcolors table
        for(GroupColorModel groupColorModel : product.getGroupColors()) {
            Optional<GroupColorModel> groupColorModelOptional =
                    groupColorService.findById(groupColorModel.getId());

            if(groupColorModelOptional.isEmpty()) throw new GroupColorNotExistsException("Group color id:" + groupColorModel.getId() +" not found");

            groupColorModelList.add(groupColorModelOptional.get());
        }


        if (!unitOptional.isPresent()) {
            throw new ProductNotFoundException("Unit not found");
        }

        Optional<ProductModel> productOptional = productService.findById(Math.toIntExact(id));

        if (!productOptional.isPresent()) {
            throw new ProductNotFoundException("Product not found");
        }

        ProductModel existingProduct = productOptional.get();

        //se o group id for existente ele tenta
        if(groupId != null) {
            Optional<ProductGroupModel> groupOptional = groupService.findById(Math.toIntExact(groupId));
            if (!groupOptional.isPresent()) {
                throw  new GroupNotFoundException("Group not found for this product");
            }
                ProductGroupModel group = groupOptional.get();
                existingProduct.setProductGroup(group);

        }
        UnitModel unit = unitOptional.get();

        existingProduct.setUnit(unit);
        existingProduct.setProductName(product.getProductName());
        existingProduct.setProductBuy(product.getProductBuy());
        existingProduct.setProductFabricated(product.getProductFabricated());
        existingProduct.setProductGeneric(product.getProductGeneric());
        existingProduct.setProductPrimary(product.getProductPrimary());
        existingProduct.setProductIntermediary(product.getProductIntermediary());
        existingProduct.setProductFinal(product.getProductFinal());
        existingProduct.setProductPrice(product.getProductPrice());
        existingProduct.setGroupColors(groupColorModelList);
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
        Optional<UnitModel> unitOptional = Optional.ofNullable(product.getUnit());
        Optional<ProductGroupModel> groupOptional = Optional.ofNullable(product.getProductGroup());

        Set<GroupColorModel> groupColorModelList = new HashSet<>();
        //verify if groupcolor exist in groupcolors table
        for(GroupColorModel groupColorModel : product.getGroupColors()) {
            Optional<GroupColorModel> groupColorModelOptional =
                    groupColorService.findById(groupColorModel.getId());

            if(groupColorModelOptional.isEmpty()) throw new GroupColorNotExistsException("Group color id:" + groupColorModel.getId() +" not found");

            groupColorModelList.add(groupColorModelOptional.get());
        }

        product.setGroupColors(groupColorModelList);


        //gives error if doest exists unit
        if(unitOptional.isEmpty()){

            throw new UnitNotFoundException("Unit not found");
        }
        else {
            UnitModel unit = unitOptional.get();
            product.setUnit(unit);
            unitService.findById(Math.toIntExact(unit.getId()));
        }

        //group and color can be nullable
        if (groupOptional.isPresent()) {

            ProductGroupModel group = groupOptional.get();
            product.setProductGroup(group);
            groupService.findById(Math.toIntExact(group.getId()));
        }


        ProductModel savedProduct = productService.save(product);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

}
