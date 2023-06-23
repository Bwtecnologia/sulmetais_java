package com.bwteconologia.sulmetais.controllers;


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

import javax.swing.text.html.Option;
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
                                                                      @RequestParam(required = false) Long unitId, @RequestParam(required = false) Long productGroupId) {

        Set<GroupColorModel> groupColorModelList = new HashSet<>();
        Optional<List<ProductGroupModel>> groupOptional = Optional.ofNullable(product.getProductGroups());
        //verify if groupcolor exist in groupcolors table
        for(GroupColorModel groupColorModel : product.getGroupColors()) {
            Optional<GroupColorModel> groupColorModelOptional =
                    groupColorService.findById(groupColorModel.getId());

            if(groupColorModelOptional.isEmpty()) throw new GroupColorNotExistsException("Group color id:" + groupColorModel.getId() +" not found");

            groupColorModelList.add(groupColorModelOptional.get());
        }

        Optional<ProductModel> productOptional = productService.findById(Math.toIntExact(id));

        if (!productOptional.isPresent()) {
            throw new ProductNotFoundException("Product not found");
        }

        ProductModel existingProduct = productOptional.get();

        if(unitId != null){
            Optional<UnitModel> unitOptional = unitService.findById(Math.toIntExact(unitId));
            if (!unitOptional.isPresent()) {
                throw new ProductNotFoundException("Unit not found");
            }
            UnitModel unit = unitOptional.get();
            existingProduct.setUnit(unit);

        }



        //se o group id for existente ele tenta
//        if(productGroupId != null) {
//            Optional<ProductGroupModel> groupOptional = groupService.findById(Math.toIntExact(productGroupId));
//            if (!groupOptional.isPresent()) {
//                throw  new GroupNotFoundException("Group not found for this product");
//            }
//                ProductGroupModel group = groupOptional.get();
//                existingProduct.setProductGroup(group);
//        }

        if (groupOptional.isPresent()) {

            List<ProductGroupModel> group = new ArrayList<>();


            for (ProductGroupModel groupModel : groupOptional.get()) {
                Optional<ProductGroupModel> optionalGroup =
                        groupService.findById(Math.toIntExact(groupModel.getId()));

                if(optionalGroup.isEmpty()) throw new GroupNotFoundException("Group with id not found");
                group.add(optionalGroup.get());
            }

            product.setProductGroups(group);
        }

        if (groupOptional.isPresent()) {

            List<ProductGroupModel> group = new ArrayList<>();


            for (ProductGroupModel groupModel : groupOptional.get()) {
                Optional<ProductGroupModel> optionalGroup =
                        groupService.findById(Math.toIntExact(groupModel.getId()));

                if(optionalGroup.isEmpty()) throw new GroupNotFoundException("Group with id not found");
                group.add(optionalGroup.get());
            }

            product.setProductGroups(group);
        }


        existingProduct.setProductGroups(product.getProductGroups());
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
        Optional<List<ProductGroupModel>> groupOptional = Optional.ofNullable(product.getProductGroups());

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

            List<ProductGroupModel> group = new ArrayList<>();


            for (ProductGroupModel groupModel : groupOptional.get()) {
                Optional<ProductGroupModel> optionalGroup =
                        groupService.findById(Math.toIntExact(groupModel.getId()));

                if(optionalGroup.isEmpty()) throw new GroupNotFoundException("Group with id not found");
                group.add(optionalGroup.get());
            }

            product.setProductGroups(group);
        }


        ProductModel savedProduct = productService.save(product);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

}
